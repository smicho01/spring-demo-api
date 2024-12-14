package org.severinu.demoapi.api.files;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final AmazonS3 amazonS3;

    private final TransferManager transferManager;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    public FileBaseResponse uploadFileAndMetadata(UploadFileMetadata uploadFileMetadata, MultipartFile multipartFile) throws TikaException, IOException {
        String fileMediaType = determineMediaType(multipartFile);
        uploadFileMetadata.setFileType(fileMediaType);

        String fileId = UUID.randomUUID().toString();

        FileBaseResponse uploadFileBaseResponse = completeFileUpload(uploadFileMetadata, multipartFile, fileId);

        return uploadFileBaseResponse;
    }

    private FileBaseResponse completeFileUpload(UploadFileMetadata uploadFileMetadata, MultipartFile multipartFile, String fileId) throws IOException {
        // TODO: upload metadata to DB

        // Upload to S3
        FileS3Identifier fileS3Identifier = storeFile(uploadFileMetadata, multipartFile, fileId);
        log.info("File S3 Identifier: {}", fileS3Identifier);

        return FileBaseResponse.builder()
                .fileId(fileId)
                .message(StatusType.STORED.getType())
                .build();
    }

    public FileS3Identifier storeFile(UploadFileMetadata uploadFileMetadata,
                                      MultipartFile multipartFile,
                                      String fileId) throws IOException {
        // Build metadata
        ObjectMetadata metaData = buildObjectMetadata(new ObjectMetadata(), uploadFileMetadata);
        metaData.setContentEncoding(StandardCharsets.UTF_8.name());
        metaData.setContentLength(multipartFile.getSize());

        // Set content type if provided
        String fileType = uploadFileMetadata.getFileType();
        if (fileType != null && !fileType.isEmpty()) {
            metaData.setContentType(fileType);
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileId,
                new ByteArrayInputStream(multipartFile.getBytes()),
                metaData);

        try {
            Upload upload = transferManager.upload(putObjectRequest);
            upload.waitForUploadResult();
            return new FileS3Identifier(putObjectRequest.getBucketName(), putObjectRequest.getKey());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMetadata buildObjectMetadata(ObjectMetadata objectMetadata, UploadFileMetadata uploadFileMetadata) {
        objectMetadata.addUserMetadata("filename", uploadFileMetadata.getFileName());
        objectMetadata.addUserMetadata("ownerName", uploadFileMetadata.getOwnerName());
        objectMetadata.addUserMetadata("happy", "puppy");
        return objectMetadata;
    }

    private String determineMediaType(MultipartFile multipartFile) throws TikaException, IOException {
        TikaConfig tika = new TikaConfig();
        Metadata tikaMetadata = new Metadata();
        tikaMetadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, multipartFile.getOriginalFilename());
        MediaType mediaType = tika.getDetector().detect(TikaInputStream.get(multipartFile.getInputStream()), tikaMetadata);
        return mediaType.toString();
    }

}

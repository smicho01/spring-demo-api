package org.severinu.demoapi.api.files.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.RequiredArgsConstructor;
import org.severinu.demoapi.api.files.FileS3Identifier;
import org.severinu.demoapi.api.files.metadata.UploadFileMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FileS3StorageService {

    private final TransferManager transferManager;
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

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

}

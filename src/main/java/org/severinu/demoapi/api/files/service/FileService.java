package org.severinu.demoapi.api.files.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.mime.MediaType;
import org.severinu.demoapi.api.files.FileS3Identifier;
import org.severinu.demoapi.api.files.StatusType;
import org.severinu.demoapi.api.files.metadata.UploadFileMetadata;
import org.severinu.demoapi.api.files.response.FileBaseResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileDBStorageService fileDBStorageService;
    private final FileS3StorageService fileS3StorageService;

    public FileBaseResponse uploadFileAndMetadata(UploadFileMetadata uploadFileMetadata, MultipartFile multipartFile) throws TikaException, IOException {
        String fileMediaType = determineMediaType(multipartFile);
        uploadFileMetadata.setFileType(fileMediaType);

        String fileId = UUID.randomUUID().toString();

        return completeFileUpload(uploadFileMetadata, multipartFile, fileId);
    }

    private FileBaseResponse completeFileUpload(UploadFileMetadata uploadFileMetadata, MultipartFile multipartFile, String fileId) throws IOException {
        // Upload to S3
        FileS3Identifier fileS3Identifier = fileS3StorageService.storeFile(uploadFileMetadata, multipartFile, fileId);
        log.info("File S3 Identifier: {}", fileS3Identifier);

        // Save metadata in DB
        fileDBStorageService.saveFileMetadata(fileS3Identifier, uploadFileMetadata, fileId, multipartFile.getSize());
        log.info("File metadata stored in DB");

        return FileBaseResponse.builder()
                .fileId(fileId)
                .message(StatusType.STORED.getType())
                .build();
    }

    private String determineMediaType(MultipartFile multipartFile) throws TikaException, IOException {
        TikaConfig tika = new TikaConfig();
        Metadata tikaMetadata = new Metadata();
        tikaMetadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, multipartFile.getOriginalFilename());
        MediaType mediaType = tika.getDetector().detect(TikaInputStream.get(multipartFile.getInputStream()), tikaMetadata);
        return mediaType.toString();
    }

}

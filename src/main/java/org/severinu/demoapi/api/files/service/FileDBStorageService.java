package org.severinu.demoapi.api.files.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.files.FileS3Identifier;
import org.severinu.demoapi.api.files.metadata.FileMetadata;
import org.severinu.demoapi.api.files.metadata.FileMetadataRepository;
import org.severinu.demoapi.api.files.metadata.UploadFileMetadata;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileDBStorageService {

    private final FileMetadataRepository fileMetadataRepository;

    public void saveFileMetadata(FileS3Identifier fileS3Identifier, UploadFileMetadata uploadFileMetadata, String fileId, long size) {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileId(fileId);
        fileMetadata.setFileName(uploadFileMetadata.getFileName());
        fileMetadata.setOwnerId(uploadFileMetadata.getOwnerId());
        fileMetadata.setContentType(uploadFileMetadata.getFileType());
        fileMetadata.setSize(size);
        fileMetadata.setUploadTime(LocalDateTime.now());
        fileMetadata.setS3bucketName(fileS3Identifier.getS3BucketName());
        fileMetadata.setS3Key(fileS3Identifier.getS3FileKey());
        fileMetadata.setApplicationId(uploadFileMetadata.getApplicationId());

        fileMetadataRepository.save(fileMetadata);
    }
}

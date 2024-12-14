package org.severinu.demoapi.api.files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        fileMetadata.setOwnerName(uploadFileMetadata.getOwnerName());
        fileMetadata.setContentType(uploadFileMetadata.getFileType());
        fileMetadata.setSize(size);
        fileMetadata.setUploadTime(LocalDateTime.now());
        fileMetadata.setS3bucketName(fileS3Identifier.getS3BucketName());
        fileMetadata.setS3Key(fileS3Identifier.getS3FileKey());

        fileMetadataRepository.save(fileMetadata);
    }
}

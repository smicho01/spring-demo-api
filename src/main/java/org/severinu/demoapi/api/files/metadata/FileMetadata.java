package org.severinu.demoapi.api.files.metadata;

import lombok.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "file_metadata")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FileMetadata {
    @Id
    private String id;
    private String fileId;
    private String fileName;
    private String ownerName;
    private String contentType;
    private long size;
    private String s3Id;
    private LocalDateTime uploadTime;
    private String s3bucketName;
    private String s3Key;

}

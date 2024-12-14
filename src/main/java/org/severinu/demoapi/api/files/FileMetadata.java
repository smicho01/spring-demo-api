package org.severinu.demoapi.api.files;

import lombok.*;
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
    private String fileName;
    private String contentType;
    private long size;
    private String s3Id;

}

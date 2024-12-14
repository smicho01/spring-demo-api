package org.severinu.demoapi.api.files;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileS3Identifier {
    private String s3BucketName;
    private String s3FileKey;

    @Override
    public String toString() {
        return "{" +
                "s3BucketName='" + s3BucketName + '\'' +
                ", s3FileKey='" + s3FileKey + '\'' +
                '}';
    }
}

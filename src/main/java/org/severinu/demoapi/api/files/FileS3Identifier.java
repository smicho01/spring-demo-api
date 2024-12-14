package org.severinu.demoapi.api.files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileS3Identifier {
    private String s3BucketName;
    private String s3FileKey;
}

package org.severinu.demoapi.api.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadFileMetadata {

    @JsonProperty
    private String fileName;

    @JsonProperty
    private String ownerName;

    @JsonProperty
    private String fileType;

}

package org.severinu.demoapi.api.files.metadata;

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
    private String ownerId;

    @JsonProperty
    private String fileType;

    @JsonProperty
    private String applicationId;

}

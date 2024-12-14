package org.severinu.demoapi.api.files.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileBaseResponse extends BaseResponse  {

    @JsonProperty
    private String fileId;

    @JsonProperty
    private String message;


    @Override
    public String toJsonString() {
        JSONObject metadata = new JSONObject();
        metadata.put("fileId", fileId);
        metadata.put("message", message);
        return message;
    }
}

package org.severinu.demoapi.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.severinu.demoapi.api.view.View;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonView(value = { View.Normal.class })
public class FilesSearchResultResponse extends SearchResultResponse {

    @JsonProperty("files")
    private List<String> files;

    @JsonProperty("location")
    @JsonView(value = { View.Extended.class})
    protected String location;

    @JsonProperty("type")
    protected String type = "files";
}

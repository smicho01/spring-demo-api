package org.severinu.demoapi.api.files;

import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.severinu.demoapi.api.files.metadata.UploadFileMetadata;
import org.severinu.demoapi.api.files.response.BaseResponse;
import org.severinu.demoapi.api.files.response.FileBaseResponse;
import org.severinu.demoapi.api.files.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<BaseResponse> uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile,
                                                   @RequestParam("metadata") UploadFileMetadata uploadFileMetadata) throws IOException, TikaException {
        FileBaseResponse fileBaseResponse = fileService.uploadFileAndMetadata(uploadFileMetadata, multipartFile);
        return new ResponseEntity<>(fileBaseResponse, HttpStatus.CREATED);
    }

}

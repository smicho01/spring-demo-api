package org.severinu.demoapi.api.files;

import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {

    private final FileService fileService;

    private final FileMetadataRepository fileMetadataRepository;


    @PostMapping
    public Map<String, String> uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile,
                                          @RequestParam("metadata") UploadFileMetadata uploadFileMetadata) throws IOException, TikaException {


        fileService.uploadFileAndMetadata(uploadFileMetadata, multipartFile);

//        // Save metadata to MongoDB
//        FileMetadata metadata = new FileMetadata();
//        //metadata.setFileName(file.getOriginalFilename());
//        metadata.setFileName(uploadFileMetadata.getFileName());
//        metadata.setOwnerName(uploadFileMetadata.getOwnerName());
//        metadata.setContentType(multipartFile.getContentType());
//        metadata.setSize(multipartFile.getSize());
//
//
//        fileMetadataRepository.save(metadata);

        // Response
        Map<String, String> response = new HashMap<>();
//        response.put("fileUrl", fileUrl);
        response.put("message", "File uploaded successfully!");
        return response;
    }


}

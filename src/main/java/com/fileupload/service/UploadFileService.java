package com.fileupload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UploadFileService {
    @Autowired
    RestTemplate restTemplate;
    public String callFileUploadProducer(String fileName, byte[] docContent){
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("messageFile")
                .filename(fileName)
                .build();

        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(docContent, fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        System.out.println("Calling FileUpload Producer");
        ResponseEntity<String> response = restTemplate
                .postForEntity("http://localhost:8080/avro/producer/file", requestEntity, String.class);
        System.out.println("FileUpload Producer called successfully");
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }
        return "Error in File Upload Service";
    }
}

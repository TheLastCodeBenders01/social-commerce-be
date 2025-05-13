package com.thelastcodebenders.social_commerce_be.adapter;

import com.thelastcodebenders.social_commerce_be.exceptions.SocialCommerceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceAdapter {

    @Value("${superbase.auth-token}")
    private String SUPERBASE_AUTH_TOKEN;
    private String BASE_URL = "https://ublskkqnccslroaritlt.supabase.co/storage/v1/object/public/social-commerce-content//";
    private final String SUPABASE_PROJECT_URL = "https://ublskkqnccslroaritlt.supabase.co";
    private final String BUCKET_NAME = "social-commerce-content";
    private final RestTemplate restTemplate;

    public List<String> uploadFiles(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                file = renameMultipartFile(file);
                String uploadUrl = SUPABASE_PROJECT_URL + "/storage/v1/object/" + BUCKET_NAME + "/" + file.getOriginalFilename();

                log.info("File name is: {}" , file.getOriginalFilename());

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + SUPERBASE_AUTH_TOKEN);
                headers.setContentType(MediaType.valueOf(file.getContentType()));
                headers.setContentLength(file.getBytes().length);

                HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

                restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);

                log.info("Saved file: {}", file.getOriginalFilename());
                fileNames.add(file.getOriginalFilename());

            } catch (Exception e) {
                log.info("Error uploading file: {}", file.getOriginalFilename());
            }
        }
        return fileNames;
    }

    public String uploadFile(MultipartFile file) {
        try {
            file = renameMultipartFile(file);
            String uploadUrl = SUPABASE_PROJECT_URL + "/storage/v1/object/" + BUCKET_NAME + "/" + file.getOriginalFilename();

            log.info("File name is: {}" , file.getOriginalFilename());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + SUPERBASE_AUTH_TOKEN);
            headers.setContentType(MediaType.valueOf(file.getContentType()));
            headers.setContentLength(file.getBytes().length);

            HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

            restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);

            log.info("Saved file: {}", file.getOriginalFilename());

        } catch (Exception e) {
            log.info("Error uploading file: {}", file.getOriginalFilename());
            throw new SocialCommerceException("Unable to upload file because " + e.getMessage());
        }
        return file.getOriginalFilename();
    }

    public String buildFileUri(String fileName) {
        return String.format("%s%s", BASE_URL, fileName);
    }


    public MultipartFile renameMultipartFile(MultipartFile originalFile) throws IOException {
        String newFileName = UUID.randomUUID() + originalFile.getOriginalFilename();
        return new MockMultipartFile(
                newFileName,                     // new file name
                newFileName,                     // new original file name
                originalFile.getContentType(),   // keep the same content type
                originalFile.getInputStream()    // same file content
        );
    }
}

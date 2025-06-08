package com.thelastcodebenders.social_commerce_be.controllers;

import com.thelastcodebenders.social_commerce_be.adapter.FileServiceAdapter;
import com.thelastcodebenders.social_commerce_be.models.dto.FileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("file")
@RestController
public class FileController {

    private final FileServiceAdapter fileServiceAdapter;

    @PostMapping("upload")
    public String uploadFile(@ModelAttribute FileRequest request) {
        return fileServiceAdapter.buildFileUri(fileServiceAdapter.uploadFileToPinata(request.getFile()));
    }
}

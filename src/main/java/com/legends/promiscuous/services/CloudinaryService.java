package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryService implements CloudService{
    @Override
    public ApiResponse<String> upload(MultipartFile file) {
        return null;
    }
}

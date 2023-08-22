package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    ApiResponse<?> upload(MultipartFile file);
}

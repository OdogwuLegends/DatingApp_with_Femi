package com.legends.promiscuous.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.legends.promiscuous.config.AppConfig;
import com.legends.promiscuous.dtos.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryService implements CloudService{
    @Autowired
    private final AppConfig appConfig;
    @Override
    public ApiResponse<?> upload(MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary();
        Uploader uploader = cloudinary.uploader();
        try{
            Map<?,?> response =  uploader.upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id","Promiscuous/Users/profile_images",
                    "api_key", appConfig.getCloudKey(),
                    "api_secret", appConfig.getCloudSecret(),
                    "cloud_name", appConfig.getCloudName(),
                    "secure", true
            ));
            return ApiResponse.builder()
                            .data(response.get("url").toString())
                            .build();
        }catch (IOException exception){
            throw new RuntimeException("File upload failed");
        }

    }
}

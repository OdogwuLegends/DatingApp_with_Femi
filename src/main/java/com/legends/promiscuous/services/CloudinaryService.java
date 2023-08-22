package com.legends.promiscuous.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.legends.promiscuous.dtos.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudinaryService implements CloudService{
    @Override
    public ApiResponse<String> upload(MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary();
        Uploader uploader = cloudinary.uploader();
        try{
            uploader.upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id","",
                    "api_key", "",
                    "api_secret", "",
                    "cloud_name", "",
                    "secure", true
            ))
        }catch (IOException exception){
            throw new RuntimeException("File upload failed");
        }
        return null;
    }
}

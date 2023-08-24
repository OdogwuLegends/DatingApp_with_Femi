package com.legends.promiscuous.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String upload(MultipartFile file);

}

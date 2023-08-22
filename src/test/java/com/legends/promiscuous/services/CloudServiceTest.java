package com.legends.promiscuous.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class CloudServiceTest{

    @Autowired
    private CloudService cloudService;


    @Test
    void testUploadFile(){
        MultipartFile file = new MockMultipartFile();
    }
}

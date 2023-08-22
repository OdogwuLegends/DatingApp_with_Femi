package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.legends.promiscuous.utils.AppUtil.TEST_IMG_LOCATION;

@SpringBootTest
public class CloudServiceTest{

    @Autowired
    private CloudService cloudService;


    @Test
    void testUploadFile() throws IOException {
        Path path = Paths.get(TEST_IMG_LOCATION);
        try(InputStream inputStream =  Files.newInputStream(path)){
        MultipartFile file = new MockMultipartFile("testImage",inputStream);
        ApiResponse<String> response = cloudService.upload(file);
        }catch (IOException exception){
            throw new RuntimeException(":(");
        }
    }
}

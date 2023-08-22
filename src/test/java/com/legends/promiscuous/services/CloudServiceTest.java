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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CloudServiceTest{
    @Autowired
    private CloudService cloudService;


    @Test
    void testUploadFile() throws IOException {
        Path path = Paths.get(TEST_IMG_LOCATION);
        try(InputStream inputStream =  Files.newInputStream(path)){
        MultipartFile file = new MockMultipartFile("testImage",inputStream);
        ApiResponse<?> response = cloudService.upload(file);
        assertNotNull(response);
        assertThat(response.getData()).isNotNull();
        }catch (IOException exception){
            throw new RuntimeException(":(");
        }
    }
}

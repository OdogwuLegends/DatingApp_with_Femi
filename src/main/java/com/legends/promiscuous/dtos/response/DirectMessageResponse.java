package com.legends.promiscuous.dtos.response;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectMessageResponse {
    private MultipartFile profileImage;
    private String firstName;
    private String lastName;
    private String message;
}

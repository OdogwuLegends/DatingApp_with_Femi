package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.response.UploadMediaResponse;
import com.legends.promiscuous.enums.Reaction;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    UploadMediaResponse uploadProfilePicture(MultipartFile file);
    UploadMediaResponse uploadMedia(MultipartFile file);
    String likeOrDislike(Reaction reaction, Long userId);
}

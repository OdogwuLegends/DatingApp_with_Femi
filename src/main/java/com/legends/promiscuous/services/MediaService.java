package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.MediaReactionRequest;
import com.legends.promiscuous.dtos.response.UploadMediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    UploadMediaResponse uploadProfilePicture(MultipartFile file);
    UploadMediaResponse uploadMedia(MultipartFile file);
    String reactToMedia(MediaReactionRequest mediaReactionRequest);
}

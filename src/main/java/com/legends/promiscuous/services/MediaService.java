package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.MediaReactionRequest;
import com.legends.promiscuous.dtos.response.UploadMediaResponse;
import com.legends.promiscuous.models.Media;
import com.legends.promiscuous.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    UploadMediaResponse uploadProfilePicture(MultipartFile file);
    UploadMediaResponse uploadMedia(MultipartFile file, User user);
    String reactToMedia(MediaReactionRequest mediaReactionRequest);
    Media getMediaByUser(User user);
}

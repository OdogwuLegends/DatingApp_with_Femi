package com.legends.promiscuous.services;

import com.legends.promiscuous.dtos.requests.MediaReactionRequest;
import com.legends.promiscuous.dtos.response.UploadMediaResponse;
import com.legends.promiscuous.exceptions.PromiscuousBaseException;
import com.legends.promiscuous.models.Media;
import com.legends.promiscuous.models.MediaReaction;
import com.legends.promiscuous.repositories.MediaRepository;
import com.legends.promiscuous.services.cloud.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.legends.promiscuous.dtos.response.ResponseMessage.SUCCESS;
import static com.legends.promiscuous.exceptions.ExceptionMessage.MEDIA_NOT_FOUND;
import static com.legends.promiscuous.utils.AppUtil.PROFILE_PICTURE_UPDATED_MSG;

@Service
public class PromiscuousMediaService implements MediaService{
    private final CloudService cloudService;
    private final MediaRepository mediaRepository;

    @Autowired
    public PromiscuousMediaService(CloudService cloudService,
                                   MediaRepository mediaRepository){
        this.cloudService = cloudService;
        this.mediaRepository = mediaRepository;
    }
    @Override
    public UploadMediaResponse uploadMedia(MultipartFile file) {
        String url = cloudService.upload(file);
        UploadMediaResponse response = new UploadMediaResponse();
        response.setMessage(url);
        return response;
    }

    @Override
    public UploadMediaResponse uploadProfilePicture(MultipartFile file) {
        cloudService.upload(file);
        UploadMediaResponse response = new UploadMediaResponse();
        response.setMessage(PROFILE_PICTURE_UPDATED_MSG);
        return response;
    }

    @Override
    public String reactToMedia(MediaReactionRequest mediaReactionRequest) {
        Media media=mediaRepository.findById(mediaReactionRequest.getMediaId())
                .orElseThrow(()->
                        new PromiscuousBaseException(MEDIA_NOT_FOUND.name()));
        MediaReaction reaction = new MediaReaction();
        reaction.setReaction(mediaReactionRequest.getReaction());
        reaction.setUser(mediaReactionRequest.getUserId());
        media.getReactions().add(reaction);
        mediaRepository.save(media);
        return SUCCESS.name();
    }
}

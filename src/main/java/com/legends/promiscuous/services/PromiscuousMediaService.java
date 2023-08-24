package com.legends.promiscuous.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.legends.promiscuous.config.AppConfig;
import com.legends.promiscuous.dtos.response.GetUserResponse;
import com.legends.promiscuous.dtos.response.UploadMediaResponse;
import com.legends.promiscuous.enums.Reaction;
import com.legends.promiscuous.models.Media;
import com.legends.promiscuous.models.User;
import com.legends.promiscuous.repositories.MediaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.legends.promiscuous.enums.Reaction.DISLIKE;
import static com.legends.promiscuous.enums.Reaction.LIKE;
import static com.legends.promiscuous.utils.AppUtil.*;

@Service
public class PromiscuousMediaService implements MediaService{
    private final CloudService cloudService;
    private AppConfig appConfig;
    private MediaRepository mediaRepository;
    private UserService userService;

    @Autowired
    public PromiscuousMediaService(CloudService cloudService,
                                   AppConfig appConfig,
                                   MediaRepository mediaRepository,
                                   UserService userService){
        this.cloudService = cloudService;
        this.appConfig = appConfig;
        this.mediaRepository = mediaRepository;
        this.userService = userService;
    }
    @Override
    public UploadMediaResponse uploadMedia(MultipartFile file) {
        return uploadVideo(file);
    }

    @Override
    public UploadMediaResponse uploadProfilePicture(MultipartFile file) {
        cloudService.upload(file);
        UploadMediaResponse response = new UploadMediaResponse();
        response.setMessage(PROFILE_PICTURE_UPDATED_MSG);
        return response;
    }

    @Override
    public String likeOrDislike(Reaction reaction, Long userId) {
        GetUserResponse user = userService.getUserById(userId);
        User foundUser = new User();
        BeanUtils.copyProperties(user,foundUser);
        boolean isExistingUser = mediaRepository.existsByUser(foundUser);

        if(reaction == LIKE && !isExistingUser){
            Media media = new Media();
            media.setUser(foundUser);
            media.setLike(true);
            media.getReactions().add(LIKE);
            mediaRepository.save(media);
            return LIKED_MSG;
        }else if(reaction == DISLIKE && isExistingUser){
            Optional<Media> media = mediaRepository.findMediaByUserAndIsLikeIsTrue(foundUser);
            Media likedMedia = media.get();
            likedMedia.setLike(false);
            likedMedia.getReactions().remove(LIKE);
            mediaRepository.save(likedMedia);
        }
        return DISLIKED_MSG;
    }

    private UploadMediaResponse uploadVideo(MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary();
        Uploader uploader = cloudinary.uploader();
        try{
            Map<?,?> response =  uploader.upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id","Promiscuous/Users/profile_images/"+file.getName(),
                    "api_key", appConfig.getCloudKey(),
                    "api_secret", appConfig.getCloudSecret(),
                    "cloud_name", appConfig.getCloudName(),
                    "secure", true,
                    "resource_type", "auto"
            ));
//            return response.get("url").toString();
            UploadMediaResponse mediaResponse = new UploadMediaResponse();
            mediaResponse.setMessage(MEDIA_UPLOAD_SUCCESSFUL);
            return mediaResponse;
        }catch (IOException exception){
            throw new RuntimeException(FILE_UPLOAD_FAILED_MSG+exception.getMessage());
        }

    }
}

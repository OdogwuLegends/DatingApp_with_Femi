package com.legends.promiscuous.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.legends.promiscuous.config.AppConfig;
import com.legends.promiscuous.dtos.response.UploadMediaResponse;
import com.legends.promiscuous.enums.Reaction;
import com.legends.promiscuous.models.Media;
import com.legends.promiscuous.models.User;
import com.legends.promiscuous.repositories.MediaRepository;
import com.legends.promiscuous.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static com.legends.promiscuous.enums.Reaction.DISLIKE;
import static com.legends.promiscuous.enums.Reaction.LIKE;

@Service
public class PromiscuousMediaService implements MediaService{
    private final CloudService cloudService;
    private AppConfig appConfig;
    private MediaRepository mediaRepository;
    private UserRepository userRepository;

    @Autowired
    public PromiscuousMediaService(CloudService cloudService,
                                   AppConfig appConfig,
                                   MediaRepository mediaRepository,
                                   UserRepository userRepository){
        this.cloudService = cloudService;
        this.appConfig = appConfig;
        this.mediaRepository = mediaRepository;
        this.userRepository = userRepository;
    }
    @Override
    public UploadMediaResponse uploadMedia(MultipartFile file) {
        return uploadVideo(file);
    }

    @Override
    public UploadMediaResponse uploadProfilePicture(MultipartFile file) {
        cloudService.upload(file);
        UploadMediaResponse response = new UploadMediaResponse();
        response.setMessage("Profile picture updated");
        return response;
    }

    @Override
    public String likeOrDislike(Reaction reaction, Long userId) {
        User foundUser = userRepository.findById(userId).get();
        boolean isExistingUser = mediaRepository.existsByUser(foundUser);

        if(reaction == LIKE && !isExistingUser){
            Media media = new Media();
            media.setUser(foundUser);
            media.setLike(true);
            media.getReactions().add(LIKE);
            mediaRepository.save(media);
            return "Liked!";
        }else if(reaction == DISLIKE && isExistingUser){
            Optional<Media> media = mediaRepository.findMediaByUserAndIsLikeIsTrue(foundUser);
            Media likedMedia = media.get();
            likedMedia.setLike(false);
            likedMedia.getReactions().remove(LIKE);
            mediaRepository.save(likedMedia);
        }
        return "X";
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
            mediaResponse.setMessage("Media upload successful");
            return mediaResponse;
        }catch (IOException exception){
            throw new RuntimeException("File upload failed: "+exception.getMessage());
        }

    }
}

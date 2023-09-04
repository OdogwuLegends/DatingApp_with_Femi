package com.legends.promiscuous.contollers;

import com.github.fge.jsonpatch.JsonPatchException;
import com.legends.promiscuous.dtos.requests.*;
import com.legends.promiscuous.dtos.response.*;
import com.legends.promiscuous.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request){
        RegisterUserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    @GetMapping("/findById")
//    public ResponseEntity<GetUserResponse> getUserById(@RequestBody FindUserRequest request){
//        long id = request.getId();
//        GetUserResponse response = userService.getUserById(id);
//        return ResponseEntity.status(HttpStatus.FOUND).body(response);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id){
        GetUserResponse response = userService.getUserById(id);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<GetUserResponse>> getAllUser(@RequestBody FindUserRequest request){
        int page = request.getPage();
        int pageSize = request.getPageSize();
        List<GetUserResponse> response = userService.getAllUsers(page,pageSize);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
//    @PatchMapping("/{id}")
//    public ResponseEntity<UpdateUserResponse> updateUserAccount(@RequestBody JsonPatch jsonPatch, @PathVariable Long id){
//        UpdateUserResponse response = userService.updateUserProfile(jsonPatch,id);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUserProfile(@ModelAttribute UpdateUserRequest updateUserRequest, @PathVariable Long id) throws JsonPatchException {
       UpdateUserResponse response =  userService.updateProfile(updateUserRequest,id);
       return ResponseEntity.ok(response);
    }
    @PostMapping("/uploadMedia")
    public ResponseEntity<UploadMediaResponse> uploadMedia(@ModelAttribute UploadMediaRequest mediaRequest){
        MultipartFile mediaToUpload = mediaRequest.getMedia();
        UploadMediaResponse response = userService.uploadMedia(mediaToUpload);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/uploadProfilePicture")
    public ResponseEntity<UploadMediaResponse> uploadProfilePicture(@ModelAttribute UploadMediaRequest mediaRequest){
        MultipartFile mediaToUpload = mediaRequest.getMedia();
        UploadMediaResponse response = userService.uploadProfilePicture(mediaToUpload);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/react/{id}")
    public ResponseEntity<?> reactToMedia(@RequestBody MediaReactionRequest mediaReactionRequest){
        ApiResponse<?> response = userService.reactToMedia(mediaReactionRequest);
        return ResponseEntity.ok(response);
    }
}

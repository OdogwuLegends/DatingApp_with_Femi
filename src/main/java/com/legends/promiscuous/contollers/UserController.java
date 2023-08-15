package com.legends.promiscuous.contollers;

import com.legends.promiscuous.dtos.requests.FindUserRequest;
import com.legends.promiscuous.dtos.requests.RegisterUserRequest;
import com.legends.promiscuous.dtos.response.GetUserResponse;
import com.legends.promiscuous.dtos.response.RegisterUserResponse;
import com.legends.promiscuous.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request){
        RegisterUserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/findById")
    public ResponseEntity<GetUserResponse> getUserById(@RequestBody FindUserRequest request){
        long id = request.getId();
        GetUserResponse response = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<GetUserResponse>> getAllUser(@RequestBody FindUserRequest request){
        int page = request.getPage();
        int pageSize = request.getPageSize();
        List<GetUserResponse> response = userService.getAllUsers(page,pageSize);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}

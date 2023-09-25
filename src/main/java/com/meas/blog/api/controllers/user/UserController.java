package com.meas.blog.api.controllers.user;

import com.meas.blog.api.dtos.LoginBody;
import com.meas.blog.api.dtos.LoginResponse;
import com.meas.blog.api.dtos.RegistrationBody;
import com.meas.blog.exceptions.UserAlreadyExistsException;
import com.meas.blog.models.User;
import com.meas.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    /***
     * POST Mapping to handle user registration
     * @param registrationBody The registration information
     * @return Response to frontend
     */
    @PostMapping("/registration")
    public ResponseEntity registerUSer(@Valid @RequestBody RegistrationBody registrationBody){
        try{
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        }catch (UserAlreadyExistsException userException){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt = null;
        LoginResponse response = new LoginResponse();
        try {
            jwt = userService.loginUser(loginBody);
        } catch (Exception ex) {
            response.setSuccess(false);
            String reason = "TOKEN_NOT_CORRECT or SOMETHING WENT WRONG";
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            response.setJwt(jwt);
            response.setUsername(loginBody.getUsername());
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }



}

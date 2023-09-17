package com.meas.blog.api.controllers.user;

import com.meas.blog.api.dtos.RegistrationBody;
import com.meas.blog.exceptions.UserAlreadyExistsException;
import com.meas.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}

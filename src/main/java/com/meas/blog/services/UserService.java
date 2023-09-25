package com.meas.blog.services;

import com.meas.blog.api.dtos.LoginBody;
import com.meas.blog.api.dtos.RegistrationBody;
import com.meas.blog.exceptions.UserAlreadyExistsException;
import com.meas.blog.models.User;
import com.meas.blog.models.dao.UserDAO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserDAO userDAO;
    private JWTService jwtService;
    private EncryptionService encryptionService;

    public UserService(UserDAO userDAO, EncryptionService encryptionService, JWTService jwtService, EncryptionService encryptionService1) {
        this.userDAO = userDAO;
        this.jwtService = jwtService;
        this.encryptionService = encryptionService1;
    }

    public List<User> getUsers(){
        return userDAO.findAll();
    }

    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException{
        // TODO: Add exception check if user already exists
        if(userDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || userDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
        ){
           throw new UserAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());

        // encrypt password
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));

        return userDAO.save(user);
    }


    public String loginUser(LoginBody loginBody) {
        Optional<User> opUser = userDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }

    public boolean userHasPermissionToUser(User user, Long id) {
        return user.getId() == id;
    }

    public User getCurrentUser(String username) {
        Optional<User> opUser = userDAO.findByUsernameIgnoreCase(username);
        if (opUser.isPresent()) {
            User user = opUser.get();
            return user;
        }
        return null;
    }

}

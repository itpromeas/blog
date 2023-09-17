package com.meas.blog.services;

import com.meas.blog.api.dtos.RegistrationBody;
import com.meas.blog.exceptions.UserAlreadyExistsException;
import com.meas.blog.models.User;
import com.meas.blog.models.dao.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO, EncryptionService encryptionService) {
        this.userDAO = userDAO;
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

        // TODO: encrypt password
        user.setPassword(registrationBody.getPassword());

        return userDAO.save(user);
    }
}

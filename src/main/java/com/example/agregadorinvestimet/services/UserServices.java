package com.example.agregadorinvestimet.services;
import com.example.agregadorinvestimet.controller.CreateUserDto;
import com.example.agregadorinvestimet.controller.UpdateUserDto;
import com.example.agregadorinvestimet.entities.User;
import com.example.agregadorinvestimet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServices {

    private UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){

        var entity = new User(UUID.randomUUID(), createUserDto.username(), createUserDto.email(), createUserDto.password(), Instant.now(), null);
        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional <User> getUserById(String userId){

        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(String userId){
        var userExists = userRepository.existsById(UUID.fromString(userId));

        if (userExists) {
            userRepository.deleteById(UUID.fromString(userId));
        }
    }

    public User updateUserById(String userId, UpdateUserDto updateUserDto){

        var userEntity = userRepository.findById(UUID.fromString(userId));

        if (userEntity.isPresent()) {
            var  user = userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
        return null;
    }
}

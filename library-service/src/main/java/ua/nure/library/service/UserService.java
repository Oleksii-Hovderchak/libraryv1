package ua.nure.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.library.domain.UserEntity;
import ua.nure.library.persistence.UserRepository;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository1) {
        this.userRepository = userRepository1;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}

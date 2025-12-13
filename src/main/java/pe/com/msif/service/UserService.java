package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.ConflictException;
import pe.com.msif.model.Guardian;
import pe.com.msif.model.User;
import pe.com.msif.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User Save(User entity) {
        Optional<User> user = userRepository.findByEmail(entity.getEmail());

        if(user.isPresent()) {
            throw new ConflictException("Este email ya esta en uso.");
        }

        return userRepository.save(entity);
    }
}

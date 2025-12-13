package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.msif.model.Guardian;
import pe.com.msif.model.User;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private GuardianService guardianService;

    @Transactional
    public User Save(User user, Guardian guardian) {
        Optional<Guardian> guardianExists = guardianService.FindByDni(guardian.getDni());

        if(guardianExists.isEmpty()) {
            guardianExists = Optional.ofNullable(guardianService.Save(guardian));
        }

        user.setGuardianId(guardianExists.get().getId());

        return userService.Save(user);
    }
}

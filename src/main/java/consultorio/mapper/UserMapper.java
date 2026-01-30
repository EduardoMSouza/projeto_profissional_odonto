package consultorio.mapper;

import consultorio.domain.user.User;
import consultorio.dto.request.auth.RegisterRequest;
import consultorio.dto.response.auth.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(RegisterRequest dto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setLogin(dto.getLogin());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getRole()
        );
    }
}

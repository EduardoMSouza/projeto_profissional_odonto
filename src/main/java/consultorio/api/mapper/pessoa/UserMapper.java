package consultorio.api.mapper.pessoa;

import consultorio.domain.entity.pessoa.User;
import consultorio.api.dto.request.auth.RegisterRequest;
import consultorio.api.dto.response.auth.UserResponse;
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

    public UserResponse toResponseDTO(User user) {
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getRole()
        );
    }
}
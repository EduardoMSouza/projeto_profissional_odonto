package consultorio.api.controller;

import consultorio.domain.entity.pessoa.User;
import consultorio.api.dto.request.auth.RegisterRequest;
import consultorio.api.dto.response.auth.UserResponse;
import consultorio.api.mapper.pessoa.UserMapper;
import consultorio.domain.repository.pessoa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Criar novo usuário (apenas ADMIN)
     */
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody RegisterRequest dto) {
        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new IllegalArgumentException("Login já está em uso");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setLogin(dto.getLogin());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponseDTO(saved));
    }

    /**
     * Listar todos usuários (apenas ADMIN)
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> response = users.stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Deletar usuário por ID (apenas ADMIN, não pode deletar a si mesmo)
     */
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id, @RequestAttribute String currentUserLogin) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Impede admin de se deletar
        if (user.getLogin().equals(currentUserLogin)) {
            throw new IllegalArgumentException("Não é possível deletar o próprio usuário");
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
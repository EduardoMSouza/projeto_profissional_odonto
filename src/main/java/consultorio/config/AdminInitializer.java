package consultorio.config;

import consultorio.domain.entity.pessoa.User;
import consultorio.domain.entity.enums.UserRole;
import consultorio.domain.repository.pessoa.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // Só cria admin se não existir nenhum usuário no sistema
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setId(UUID.randomUUID().toString());
            admin.setLogin("admin");
            // Mude esta senha após primeiro login!
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(UserRole.ADMIN);

            userRepository.save(admin);

            System.out.println("============================================");
            System.out.println("USUÁRIO ADMIN CRIADO AUTOMATICAMENTE");
            System.out.println("Login: admin");
            System.out.println("Senha: admin123");
            System.out.println("ALTERE A SENHA APÓS O PRIMEIRO LOGIN!");
            System.out.println("============================================");
        }
    }
}
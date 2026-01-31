package consultorio.config;

import consultorio.domain.user.User;
import consultorio.domain.user.UserRole;
import consultorio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se já existe algum usuário admin
            if (!userRepository.existsByLogin("admin")) {
                User admin = new User();
                admin.setId(UUID.randomUUID().toString());
                admin.setLogin("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(UserRole.ADMIN);

                userRepository.save(admin);
                logger.info("======================================");
                logger.info("Usuário ADMIN criado com sucesso!");
                logger.info("Login: admin");
                logger.info("Senha: admin123");
                logger.info("======================================");
                logger.warn("ATENÇÃO: Altere a senha padrão após o primeiro login!");
                logger.info("======================================");
            } else {
                logger.info("Usuário admin já existe. Inicialização ignorada.");
            }

            // Cria usuário secretaria de exemplo (opcional)
            if (!userRepository.existsByLogin("secretaria")) {
                User secretaria = new User();
                secretaria.setId(UUID.randomUUID().toString());
                secretaria.setLogin("secretaria");
                secretaria.setPassword(passwordEncoder.encode("secretaria123"));
                secretaria.setRole(UserRole.SECRETARIA);

                userRepository.save(secretaria);
                logger.info("======================================");
                logger.info("Usuário SECRETARIA criado com sucesso!");
                logger.info("Login: secretaria");
                logger.info("Senha: secretaria123");
                logger.info("======================================");
                logger.warn("ATENÇÃO: Altere a senha padrão após o primeiro login!");
                logger.info("======================================");
            }
        };
    }
}
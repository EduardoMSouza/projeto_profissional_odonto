package consultorio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    public CorsConfig(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0]))
                .allowedMethods(corsProperties.getAllowedMethods().toArray(new String[0]))
                .allowedHeaders(corsProperties.getAllowedHeaders().toArray(new String[0]))
                .allowCredentials(corsProperties.isAllowCredentials())
                .maxAge(corsProperties.getMaxAge());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origens permitidas
        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());

        // MÃ©todos HTTP permitidos
        configuration.setAllowedMethods(corsProperties.getAllowedMethods());

        // Headers permitidos
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());

        // Headers expostos ao cliente (se configurados)
        if (corsProperties.getExposedHeaders() != null && !corsProperties.getExposedHeaders().isEmpty()) {
            configuration.setExposedHeaders(corsProperties.getExposedHeaders());
        } else {
            configuration.setExposedHeaders(Arrays.asList(
                    "Access-Control-Allow-Origin",
                    "Access-Control-Allow-Credentials",
                    "Authorization"
            ));
        }

        // Permitir credenciais
        configuration.setAllowCredentials(corsProperties.isAllowCredentials());

        // Tempo de cache
        configuration.setMaxAge(corsProperties.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
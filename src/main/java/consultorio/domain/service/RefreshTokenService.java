package consultorio.domain.service;

import consultorio.domain.entity.user.token.RefreshToken;
import consultorio.domain.entity.pessoa.User;
import consultorio.domain.repository.pessoa.RefreshTokenRepository;
import consultorio.domain.repository.pessoa.UserRepository;
import consultorio.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenDurationMs;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               UserRepository userRepository,
                               JwtUtil jwtUtil) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public RefreshToken createRefreshToken(String username, HttpServletRequest request) {
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Revoga tokens antigos do usuário (opcional - pode manter múltiplos dispositivos)
        // refreshTokenRepository.revokeAllUserTokens(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .revoked(false)
                .deviceInfo(extractDeviceInfo(request))
                .ipAddress(extractIpAddress(request))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByTokenAndRevokedFalse(token);
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new IllegalArgumentException("Refresh token expirado. Por favor, faça login novamente.");
        }
        return token;
    }

    @Transactional
    public void revokeToken(String token) {
        refreshTokenRepository.revokeToken(token);
    }

    @Transactional
    public void revokeAllUserTokens(String username) {
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        refreshTokenRepository.revokeAllUserTokens(user);
    }

    @Transactional
    public void deleteByUser(String username) {
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        refreshTokenRepository.deleteByUser(user);
    }

    // Limpa tokens expirados a cada 24 horas
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void cleanExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(Instant.now());
    }

    private String extractDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent != null ? userAgent : "Unknown";
    }

    private String extractIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
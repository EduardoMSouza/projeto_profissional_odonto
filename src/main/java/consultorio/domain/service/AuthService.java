package consultorio.domain.service;

import consultorio.domain.entity.user.token.RefreshToken;
import consultorio.domain.entity.pessoa.User;

import consultorio.api.dto.request.auth.LoginRequest;
import consultorio.api.dto.request.auth.RegisterRequest;
import consultorio.api.dto.response.auth.LoginResponse;
import consultorio.api.dto.response.auth.UserResponse;
import consultorio.api.mapper.pessoa.UserMapper;
import consultorio.domain.repository.pessoa.UserRepository;
import consultorio.security.CookieUtil;
import consultorio.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final CookieUtil cookieUtil;

    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    public AuthService(UserRepository userRepository, UserMapper userMapper,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                       RefreshTokenService refreshTokenService, CookieUtil cookieUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.cookieUtil = cookieUtil;
    }

    @Transactional
    public UserResponse register(RegisterRequest dto) {
        if (userRepository.existsByLogin(dto.getLogin())) {
            throw new IllegalArgumentException("Login já está em uso");
        }

        User user = userMapper.toEntity(dto);
        user = userRepository.save(user);
        return userMapper.toResponseDTO(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest dto, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Gera access token
            String accessToken = jwtUtil.generateAccessToken(userDetails);

            // Cria refresh token no banco
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(
                    userDetails.getUsername(), request
            );

            // Define cookies HttpOnly
            cookieUtil.addAccessTokenCookie(response, accessToken, (int) (accessTokenExpiration / 1000));
            cookieUtil.addRefreshTokenCookie(response, refreshToken.getToken(), (int) (refreshTokenExpiration / 1000));

            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            return new LoginResponse(
                    null, // Não retorna token no body quando usa cookie
                    userDetails.getUsername(),
                    role,
                    "Tokens definidos em cookies HttpOnly"
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais inválidas");
        }
    }

    @Transactional
    public LoginResponse refreshToken(String refreshTokenStr, HttpServletResponse response) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenStr)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido"));

        refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();
        String newAccessToken = jwtUtil.generateAccessToken(user);

        // Atualiza cookie do access token
        cookieUtil.addAccessTokenCookie(response, newAccessToken, (int) (accessTokenExpiration / 1000));

        String role = user.getAuthorities().iterator().next().getAuthority();

        return new LoginResponse(
                null,
                user.getUsername(),
                role,
                "Access token renovado"
        );
    }

    @Transactional
    public void logout(String refreshTokenStr, HttpServletResponse response) {
        if (refreshTokenStr != null) {
            refreshTokenService.revokeToken(refreshTokenStr);
        }
        cookieUtil.deleteAllAuthCookies(response);
    }

    public UserResponse getCurrentUser(String username) {
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return userMapper.toResponseDTO(user);
    }
}
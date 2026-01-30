package consultorio.api.controller;

import consultorio.api.dto.request.auth.LoginRequest;
import consultorio.api.dto.request.auth.RegisterRequest;
import consultorio.api.dto.response.auth.LoginResponse;
import consultorio.api.dto.response.auth.UserResponse;
import consultorio.security.CookieUtil;
import consultorio.domain.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    public AuthController(AuthService authService, CookieUtil cookieUtil) {
        this.authService = authService;
        this.cookieUtil = cookieUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest dto) {
        UserResponse response = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest dto,
            HttpServletRequest request,
            HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(dto, request, response);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String refreshToken = cookieUtil.getRefreshTokenFromCookie(request)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token não encontrado"));

        LoginResponse loginResponse = authService.refreshToken(refreshToken, response);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieUtil.getRefreshTokenFromCookie(request).orElse(null);
        authService.logout(refreshToken, response);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponse response = authService.getCurrentUser(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
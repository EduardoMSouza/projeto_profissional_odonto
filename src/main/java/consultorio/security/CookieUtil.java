package consultorio.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtil {

    @Value("${jwt.cookie.access-token-name}")
    private String accessTokenCookieName;

    @Value("${jwt.cookie.refresh-token-name}")
    private String refreshTokenCookieName;

    @Value("${jwt.cookie.secure}")
    private boolean secure;

    @Value("${jwt.cookie.domain}")
    private String domain;

    public void addAccessTokenCookie(HttpServletResponse response, String token, int maxAge) {
        Cookie cookie = new Cookie(accessTokenCookieName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        if (domain != null && !domain.isEmpty()) {
            cookie.setDomain(domain);
        }
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String token, int maxAge) {
        Cookie cookie = new Cookie(refreshTokenCookieName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(maxAge);
        if (domain != null && !domain.isEmpty()) {
            cookie.setDomain(domain);
        }
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    public Optional<String> getAccessTokenFromCookie(HttpServletRequest request) {
        return getCookieValue(request, accessTokenCookieName);
    }

    public Optional<String> getRefreshTokenFromCookie(HttpServletRequest request) {
        return getCookieValue(request, refreshTokenCookieName);
    }

    private Optional<String> getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookieName.equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst();
        }
        return Optional.empty();
    }

    public void deleteAccessTokenCookie(HttpServletResponse response) {
        deleteCookie(response, accessTokenCookieName, "/");
    }

    public void deleteRefreshTokenCookie(HttpServletResponse response) {
        deleteCookie(response, refreshTokenCookieName, "/api/auth/refresh");
    }

    public void deleteAllAuthCookies(HttpServletResponse response) {
        deleteAccessTokenCookie(response);
        deleteRefreshTokenCookie(response);
    }

    private void deleteCookie(HttpServletResponse response, String cookieName, String path) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath(path);
        cookie.setMaxAge(0);
        if (domain != null && !domain.isEmpty()) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }
}
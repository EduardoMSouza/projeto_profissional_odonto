package consultorio.domain.entity.enums;


import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ADMIN("admin"),
    SECRETARIA("secretaria");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + role.toUpperCase();
    }
}
package consultorio.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String login;
    private String role;
    private String message;

    public LoginResponse(String token, String login, String role) {
        this.token = token;
        this.login = login;
        this.role = role;
    }
}

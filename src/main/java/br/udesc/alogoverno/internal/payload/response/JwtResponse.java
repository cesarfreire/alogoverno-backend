package br.udesc.alogoverno.internal.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    @Setter
    @Getter
    private Long id;
    @Getter
    @Setter
    private String refreshToken;
    @Getter
    @Setter
    private String username;
    @Setter
    @Getter
    private String email;
    @Getter
    private List<String> roles;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

}
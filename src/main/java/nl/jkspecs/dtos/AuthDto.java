package nl.jkspecs.dtos;

public class AuthDto {
    public String username;
    public String password;
    private final String accessToken;

    public AuthDto(String jwt) {
        this.accessToken = jwt;
    }

    public String getAccessToken() {
        return accessToken;
    }
}


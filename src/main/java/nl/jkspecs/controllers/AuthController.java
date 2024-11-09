package nl.jkspecs.controllers;

import nl.jkspecs.dtos.AuthDto;
import nl.jkspecs.dtos.AuthInputDto;
import nl.jkspecs.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager man, JwtService service) {
        this.authManager        = man;
        this.jwtService         = service;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> signIn(@RequestBody AuthInputDto authInputDto) {
            UsernamePasswordAuthenticationToken up =
                    new UsernamePasswordAuthenticationToken(authInputDto.getUsername(), authInputDto.getPassword());

            try {
                Authentication auth = authManager.authenticate(up);
                UserDetails ud = (UserDetails) auth.getPrincipal();
                String token = jwtService.generateToken(ud);
                AuthDto authDto = new AuthDto(token);
                authDto.username = authInputDto.getUsername();
                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .body(authDto);
            }
            catch (AuthenticationException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
            }
    }

}

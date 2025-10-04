package com.xflprflx.my_depot_backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xflprflx.my_depot_backend.model.dtos.request.LoginRequest;
import com.xflprflx.my_depot_backend.model.dtos.response.LoggedInUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${security.token-uri}")
    private String tokenUri;

    @Value("${security.client-id}")
    private String clientId;

    @Value("${security.client-secret}")
    private String clientSecret;

    @Value("${security.jwt.duration}")
    private int tokenDuration;

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "password");
            body.add("username", loginRequest.email());
            body.add("password", loginRequest.password());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(clientId, clientSecret);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            try {
                ResponseEntity<Map> tokenResponse = restTemplate.exchange(
                        tokenUri,
                        HttpMethod.POST,
                        request,
                        Map.class
                );

                Map<String, Object> tokens = tokenResponse.getBody();

                // set HttpOnly cookies
                addCookie(response, "access_token", (String) tokens.get("access_token"), this.tokenDuration);
                addCookie(response, "refresh_token", (String) tokens.get("refresh_token"), this.tokenDuration);

                // decode JWT to get user info
                String accessToken = (String) tokens.get("access_token");
                String[] parts = accessToken.split("\\.");
                String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> claims = null;
                try {
                    claims = mapper.readValue(payload, Map.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                String email = (String) claims.get("username");
                List<String> authorities = (List<String>) claims.get(("authorities"));

                LoggedInUser loggedInUser = new LoggedInUser(email, authorities);

                return ResponseEntity.ok().body(loggedInUser);

            } catch (HttpClientErrorException e) {
                return ResponseEntity.status(e.getStatusCode())
                        .body(Map.of("error", e.getResponseBodyAsString()));
            }
        }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // remove cookies
        addCookie(response, "access_token", "", 0);
        addCookie(response, "refresh_token", "", 0);
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        // Pega o refresh token do cookie
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No cookies found"));
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("refresh_token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Refresh token missing"));
        }

        // Monta request pro Authorization Server
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> tokenResponse = restTemplate.exchange(
                    tokenUri,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map<String, Object> tokens = tokenResponse.getBody();

            // Atualiza cookies HTTP-Only
            addCookie(response, "access_token", (String) tokens.get("access_token"), this.tokenDuration);
            addCookie(response, "refresh_token", (String) tokens.get("refresh_token"), this.tokenDuration);

            // decode JWT to get user info
            String accessToken = (String) tokens.get("access_token");
            String[] parts = accessToken.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> claims = null;
            try {
                claims = mapper.readValue(payload, Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            String email = (String) claims.get("username");
            List<String> authorities = (List<String>) claims.get(("authorities"));

            LoggedInUser loggedInUser = new LoggedInUser(email, authorities);

            return ResponseEntity.ok().body(loggedInUser);


        } catch (HttpClientErrorException e) {
            // Se refresh falhar (ex: expirou), limpa cookies
            addCookie(response, "access_token", "", 0);
            addCookie(response, "refresh_token", "", 0);

            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", e.getResponseBodyAsString()));
        }
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAgeSec) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // se estiver usando HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSec);
        response.addCookie(cookie);
    }

    @PreAuthorize("hasAuthority('CATEGORIES_READ')")
    @PostMapping("/teste")
    public ResponseEntity<Void> teste() {
        return ResponseEntity.ok().build();
    }
}

package com.example.hostelAvailabilitySystem.controller;

import com.example.hostelAvailabilitySystem.model.AppUser;
import com.example.hostelAvailabilitySystem.model.AuthCredentialsRequest;
import com.example.hostelAvailabilitySystem.security.utils.JwtUtil;
import com.example.hostelAvailabilitySystem.service.serviceImpl.AppUserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(path = "auth")
@CrossOrigin
@AllArgsConstructor
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtUtil jwtUtil;
    private final AppUserService appUserService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()));

            AppUser appUser = (AppUser) authenticate.getPrincipal();
            appUser.setPassword(null);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("Access-Control-Expose-Headers",AUTHORIZATION);
            responseHeaders.set("Access-Control-Expose-Headers",AUTHORIZATION);
//            responseHeaders.add("Access-Control-Allow-Origin","*");
//            responseHeaders.set("Access-Control-Allow-Origin","*");
//            responseHeaders.add("Access-Control-Allow-Methods","POST");
//            responseHeaders.set("Access-Control-Allow-Methods","POST");
            responseHeaders.add(AUTHORIZATION, jwtUtil.generateToken(appUser).toString());
            responseHeaders.set(AUTHORIZATION,jwtUtil.generateToken(appUser).toString());

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(appUser);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(path = "updatePassword")
    public String updatePassword(@RequestBody AuthCredentialsRequest request) {
        return  appUserService.updatePassword(request);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@CookieValue(name = "jwt") String token,
                                           @AuthenticationPrincipal AppUser appUser) {
        try {
            Boolean isValidToken = jwtUtil.validateToken(token, appUser);
            return ResponseEntity.ok(isValidToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout () {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .domain("cookies.domain")
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).body("ok");
    }

    @GetMapping("/test")
    public String testAPI () {
        return "Success";
    }

}

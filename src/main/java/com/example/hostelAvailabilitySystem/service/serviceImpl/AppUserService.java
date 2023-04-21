package com.example.hostelAvailabilitySystem.service.serviceImpl;

import com.example.hostelAvailabilitySystem.model.AppUser;
import com.example.hostelAvailabilitySystem.model.AuthCredentialsRequest;
import com.example.hostelAvailabilitySystem.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE =
            "user with email not found";

    @Autowired
    private final AppUserRepository appUserRepository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return appUserRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpUser(AppUser appUser) {
         boolean userExits = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

         if (userExits) {
             throw new IllegalStateException("email already taken");
         }

         String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

         appUser.setPassword(encodedPassword);

         appUserRepository.save(appUser);

         // TODO: Semd confirmation token
        return "User Registered";
    }

    public String updatePassword(AuthCredentialsRequest request) {
        boolean userExits = appUserRepository.findByEmail(request.getUsername()).isPresent();

        if (!userExits) {
            throw new IllegalStateException("email not present");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        Timestamp dateModified = new Timestamp(Calendar.getInstance().getTimeInMillis());

        appUserRepository.updatePasswordByEmail(encodedPassword, request.getUsername(), dateModified);

        // TODO: Semd confirmation token
        return "Password Updated";
    }
}

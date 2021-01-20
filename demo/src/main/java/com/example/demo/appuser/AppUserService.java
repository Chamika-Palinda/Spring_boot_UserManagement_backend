 package com.example.demo.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
private final UserRepository userRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;
private final static  String USER_NOT_FOUND_MSG="USER WITH EMAIL %s NOT FOUND";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public String signUpUser(AppUser appUser){
        boolean userExists =userRepository.findByEmail(appUser.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword =bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);
        //TODO: Send Confirmation token
        return "it Works!!!!";

    }


}

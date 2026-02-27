package edu.prathap.reminder.service;


import edu.prathap.reminder.entity.RmsUser;
import edu.prathap.reminder.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepository; // Injects the UserRepo for accessing user data

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieves user details by username from the database
        return userRepository.findByUsername(username);
    }

    public String create(String username, String password,String role) {
        // Encodes the password and creates a new User object
        System.out.println("password"+password);
        RmsUser customUser = new RmsUser();
        customUser.setUsername(username);
        customUser.setPassword(new BCryptPasswordEncoder().encode(password));
        customUser.setAuthorities(role.replace("[", "").replace("]", ""));
        /*CustomUser user = CustomUser
                .builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password)) // Encrypts the password
                .authorities("USER") // Assigns default authority
                .build();*/

        // Saves the new user to the database
        userRepository.save(customUser);

        return "Create Successfully !";
    }
}

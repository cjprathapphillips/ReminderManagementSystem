package edu.prathap.reminder.security;


import com.mysql.cj.protocol.AuthenticationProvider;
import edu.prathap.reminder.service.UserService;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
class WebSecurityConfig {

        @Autowired
        private UserService userService;
        @Bean
        public DaoAuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
    //        provider.setUserDetailsService(userService);
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }

        @Bean
        protected PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
//                                .requestMatchers("/*").permitAll()
                                .requestMatchers("/**").permitAll()
//                                .requestMatchers("/mainMenu").permitAll()
//                                .requestMatchers("/login").permitAll()
//                                .requestMatchers("/create").permitAll()
//                                .requestMatchers("/logout").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/mainMenu", true)
                        .permitAll())
                .logout(config -> config
                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true) // Explicitly invalidates the session (default behavior)
//                        .deleteCookies("JSESSIONID")
                )
                .build();
    }





}

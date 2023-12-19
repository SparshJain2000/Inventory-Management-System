package config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    @Value("${eureka.username}")
    private String username;

    @Value("${eureka.password}")
    private String password;


//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User.builder().username(username).password(passwordEncoder().encode(password)).roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        log.info("CREDS: " + username + " : " + password);
//        httpSecurity.csrf().disable().authorizeHttpRequests((authorize) -> {
//            authorize.anyRequest().authenticated();
//        }).httpBasic(Customizer.withDefaults());
//        return httpSecurity.build();
//    }

}

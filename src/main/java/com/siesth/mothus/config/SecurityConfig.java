package com.siesth.mothus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                // Allow everyone to access only the files for login, playing and the help popup
                                .requestMatchers(
                                        "/login", "/loginContent", "/registerContent", "/processRegister", "/processLogin", "/validateMailRegister", "/send-email", "/confirmEmailPopup",
                                        "/playZone", "/helpPopup", "/getYamlData", "/sendWord",
                                        "/assets/icons/**", "/assets/logos/**", "/assets/Login_Wallpaper.png",
                                        "/css/confirmEmailPopup.css", "/css/helpPopup.css", "/css/login.css", "/css/playZone.css",
                                        "/js/helpPopup.js", "/js/login.js", "/js/notify.js", "/js/playZone.js")
                                .permitAll()
                                // Block every other page with authentication
                                .anyRequest()
                                .authenticated()
                        // TODO : Change popups and other things that don't need a URL to ResourceUtils.getFile("classpath:...")
                ).formLogin(
                        form -> form
                                .loginPage("/login")

                                // Doesn't work
                                /*
                                .loginProcessingUrl("/processLogin")
                                .defaultSuccessUrl("/playZone")
                                .failureUrl("/login?loginError=true")
                                .failureHandler((request, response, exception) -> {
                                    request.getSession().setAttribute("loginError", exception.getMessage());
                                    response.sendRedirect("/login?loginError=test");
                                })
                                */
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                                .permitAll()
                );
        return http.build();
    }
}
package com.siesth.mothus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
                                // Allow everyone to access the URLs for main login page, playing and the help popup
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/login", "/logout",
                                        "/playZone", "/helpPopup", "/getYamlData", "/getTodayWordData", "/assets/SamplePublicity.png",
                                        "/assets/icons/**", "/assets/logos/**", "/assets/Login_Wallpaper.png",
                                        "/css/confirmEmailPopup.css", "/css/helpPopup.css", "/css/login.css", "/css/playZone.css",
                                        "/js/helpPopup.js", "/js/login.js", "/js/notify.js", "/js/playZone.js"
                                ).permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/sendWord"
                                ).permitAll()
                                // Allow only anonymous users to access the URLs for logging in and registering
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/loginContent", "/registerContent"
                                ).anonymous()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/processRegister"
                                ).anonymous()
                                // Allow user that need to validate their email to access the URLS for
                                // validating the email
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/confirmEmailPopup"
                                ).hasRole("PENDING") // ROLE_ is automatically prepended
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/validateMailRegister", "/timeBeforeValidationCode", "/createNewValidationCode"
                                ).hasRole("PENDING")
                                // Block every other page until the user has validated their email
                                .anyRequest()
                                .hasRole("USER")
                        // TODO : Change popups and other things that don't need a URL to ResourceUtils.getFile("classpath:...")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                // If needed, /login will redirect further
                                .defaultSuccessUrl("/login", true)
                                // TODO : Notifications no longer work, fix it
                                /*
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

    /**
     * This bean is needed to be able to use the AuthenticationManager in a controller, for example to connect the user
     * without using the /login mapping
     * @param authenticationConfiguration The authentication configuration
     * @return The authentication manager
     * @throws Exception If the authentication manager can't be created
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
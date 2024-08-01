package com.FTEL.SchoolManagementSystem.config;

import com.FTEL.SchoolManagementSystem.enums.Role;
import com.FTEL.SchoolManagementSystem.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.signerKey}") // đọc biến từ file yaml
    private String SIGNER_KEY;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RoleBasedAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login", "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/system/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/system/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/system/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/system/admin/**").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/api/user/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/user/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/user/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/user/**").authenticated()
                                .requestMatchers(HttpMethod.GET, "/course-list").authenticated()
                                .requestMatchers(HttpMethod.POST, "/enroll").authenticated()
                                .requestMatchers(HttpMethod.POST, "/unenroll").authenticated()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

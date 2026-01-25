package com.example.demo700.Security;


import org.springframework.boot.security.autoconfigure.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 🔴 CSRF off (API based app)
            .csrf(csrf -> csrf.disable())

            // 🔴 Enable CORS
            .cors(cors -> {})

            // 🔴 Stateless session (JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 🔴 Authorization rules
            .authorizeHttpRequests(auth ->
                auth
                    // ✅ VERY IMPORTANT for Flutter Web
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // ✅ Public APIs
                    .requestMatchers(
                        "/api/auth/register",
                        "/api/auth/login"
                    ).permitAll()

                    // 🔐 Everything else needs JWT
                    .anyRequest().authenticated()
            )

            // 🔴 Add JWT filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public FilterRegistrationBean<MultipartFilter> multipartFilter() {
        FilterRegistrationBean<MultipartFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MultipartFilter());
        
        return registrationBean;
    }
    
}

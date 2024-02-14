package io.github.ValterGabriell.FrequenciaAlunos.infra.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ProjectSecurityConfig {
    private final ApiKeyAuthFilter apiKeyAuthFilter;

    public ProjectSecurityConfig(ApiKeyAuthFilter apiKeyAuthFilter) {
        this.apiKeyAuthFilter = apiKeyAuthFilter;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOriginPatterns(List.of("http://localhost:3000",
                                                                       "http://localhost:3000/",
                                                                       "https://localhost:3000/",
                                                                       "https://localhost:3000",
                                                                       "https://logos-ufpa.vercel.app/",
                                                                       "https://logos-ufpa.vercel.app"));
                        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTION"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin","Authorization", "Cache-Control", "Content-Type","X-API-KEY"));
                        return configuration;
                    }
                })
                .and()
                .csrf().disable()
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class) // Add our custom filter
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/v1/admin/insert/**", "/api/v1/admin/login/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}

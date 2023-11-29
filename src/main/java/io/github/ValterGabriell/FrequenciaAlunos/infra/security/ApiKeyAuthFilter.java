package io.github.ValterGabriell.FrequenciaAlunos.infra.security;

import io.github.ValterGabriell.FrequenciaAlunos.domain.Admin;
import io.github.ValterGabriell.FrequenciaAlunos.domain.ApiKeyEntity;
import io.github.ValterGabriell.FrequenciaAlunos.service.AdmService;
import io.github.ValterGabriell.FrequenciaAlunos.service.ApiKeyService;
import io.github.ValterGabriell.FrequenciaAlunos.util.CheckURL;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private final ApiKeyService apiKeyService;
    private final AdmService admService;

    public ApiKeyAuthFilter(ApiKeyService apiKeyService, AdmService admService) {
        this.apiKeyService = apiKeyService;
        this.admService = admService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        List<String> allowedUrls = Arrays.asList("/api/v1/admin/insert", "/api/v1/admin/login", "/swagger-ui");
        String requestUrl = request.getServletPath();

        if (allowedUrls.stream().anyMatch(requestUrl::startsWith)) {
            filterChain.doFilter(request, response);
        } else {
            // Get the API key and secret from request headers
            String requestApiKey = request.getHeader("X-API-KEY");
            String tenant = request.getParameter("tenant");
            // Validate the key and secret
            ApiKeyEntity apiKey = apiKeyService.getApiKey(tenant);
            if (apiKey.getApiKey().equals(requestApiKey)) {
                Authentication authentication = createAuthentication(apiKey);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                // Reject the request and send an unauthorized error
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized");
            }
        }

    }

    private Authentication createAuthentication(ApiKeyEntity apiKeyEntity) {
        Admin admin = admService.getAdminByTenant(Integer.valueOf(apiKeyEntity.getTenant()));
        String username = admin.getCnpj();
        String password = admin.getPassword();

        List<GrantedAuthority> authorities = new ArrayList<>();
        admin.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getValue())));
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }
}

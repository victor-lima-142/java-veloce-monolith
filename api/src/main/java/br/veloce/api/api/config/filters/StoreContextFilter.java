package br.veloce.api.api.config.filters;

import br.veloce.api.domain.models.company.User;
import br.veloce.api.domain.repositories.company.UserStoreRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StoreContextFilter extends OncePerRequestFilter {

    private static final String[] WHITE_LIST_URL = {
            "/v3/docs",
            "/v3/docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };
    private final UserStoreRepository userStoreRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestPath = request.getServletPath();

        for (String pattern : WHITE_LIST_URL) {
            if (pathMatcher.match(pattern, requestPath)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String storeId = request.getHeader("X-STORE-ID");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (storeId != null && authentication != null && authentication.getPrincipal() instanceof User user) {

            List<GrantedAuthority> authorities = userStoreRepository
                    .findByUserIdAndStoreId(user.getId(), storeId)
                    .filter(userStore -> userStore.getRoleOverride() != null)
                    .map(userStore -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + userStore.getRoleOverride().name()))
                    .map(List::of)
                    .orElseGet(() -> List.copyOf(user.getAuthorities()));

            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                    user,
                    authentication.getCredentials(),
                    authorities
            );

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        filterChain.doFilter(request, response);
    }
}

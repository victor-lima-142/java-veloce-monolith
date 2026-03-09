package br.veloce.api.api.services;

import br.veloce.api.api.dtos.auth.*;
import br.veloce.api.api.exceptions.UnauthorizedException;
import br.veloce.api.domain.models.company.Company;
import br.veloce.api.domain.models.company.Store;
import br.veloce.api.domain.models.company.User;
import br.veloce.api.domain.repositories.company.UserRepository;
import br.veloce.api.services.JwtService;
import br.veloce.api.services.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("E-mail already registered");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest request) {
        String email = jwtService.extractUsername(request.refreshToken());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!jwtService.isTokenValid(request.refreshToken(), user)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        return buildAuthResponse(user);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            // Trigger password reset e-mail here
            // ex: emailService.sendPasswordReset(user);
        });
    }

    public void logout(User user, String token) {
        if (user == null) throw new UnauthorizedException("User not found");
        tokenBlacklistService.blacklist(token);
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        List<CompanySelector> companies = userRepository.findCompaniesOfUser(user.getId()).stream().map(CompanySelector::new).toList();
        List<StoreSelector> stores = userRepository.findStoresOfUser(user.getId()).stream().map(StoreSelector::new).toList();
        return new AuthResponse(accessToken, refreshToken, companies, stores);
    }
}
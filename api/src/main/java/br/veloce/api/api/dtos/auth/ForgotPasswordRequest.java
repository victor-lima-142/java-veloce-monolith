package br.veloce.api.api.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record ForgotPasswordRequest(
        @Getter
        @Email(message = "Invalid e-mail")
        @NotBlank(message = "E-mail is required")
        @JsonProperty(value = "email", required = true)
        String email
) {
}

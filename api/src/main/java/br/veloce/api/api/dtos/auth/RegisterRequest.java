package br.veloce.api.api.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public record RegisterRequest(

        @Getter
        @NotBlank(message = "Name is required")
        @JsonProperty(value = "name", required = true)
        String name,

        @Getter
        @Email(message = "Invalid e-mail")
        @NotBlank(message = "E-mail is required")
        @JsonProperty(value = "email", required = true)
        String email,

        @Getter
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        @JsonProperty(value = "password", required = true)
        String password,

        @Getter
        @NotBlank(message = "Company ID is required")
        @JsonProperty(value = "company_id", required = true)
        String companyId
) {
}
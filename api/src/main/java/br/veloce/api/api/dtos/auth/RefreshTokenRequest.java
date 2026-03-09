package br.veloce.api.api.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public record RefreshTokenRequest(
        @Getter
        @Setter
        @JsonProperty(value = "refresh_token", required = true)
        String refreshToken
) {
}

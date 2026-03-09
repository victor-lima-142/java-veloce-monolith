package br.veloce.api.api.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public record AuthResponse(
        @Getter
        @Setter
        @JsonProperty(value = "access_token", required = true)
        String accessToken,

        @Getter
        @Setter
        @JsonProperty(value = "refresh_token", required = true)
        String refreshToken,

        @Getter
        @Setter
        @JsonProperty(value = "companies", required = true)
        List<CompanySelector> companies,

        @Getter
        @Setter
        @JsonProperty(value = "stores", required = true)
        List<StoreSelector> stores
) {
}

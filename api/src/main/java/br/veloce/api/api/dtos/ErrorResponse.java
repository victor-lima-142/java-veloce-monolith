package br.veloce.api.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @NotBlank
    @NotNull
    @JsonProperty(required = true)
    private int status;

    @NotBlank
    @NotNull
    @JsonProperty(required = true)
    private String error;

    @NotBlank
    @NotNull
    @JsonProperty(required = true)
    private String message;

    @NotBlank
    @NotNull
    @JsonProperty(required = true)
    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}


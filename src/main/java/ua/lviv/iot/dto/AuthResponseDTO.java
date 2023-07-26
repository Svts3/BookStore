package ua.lviv.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponseDTO {

    private String accessToken;
    private String tokenType="Bearer ";

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}

package ua.lviv.iot.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class RegisterDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String password;

}

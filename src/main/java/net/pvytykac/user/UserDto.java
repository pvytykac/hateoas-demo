package net.pvytykac.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UserDto {

    String id;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;

}

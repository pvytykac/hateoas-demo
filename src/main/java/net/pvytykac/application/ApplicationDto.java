package net.pvytykac.application;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ApplicationDto {

    String id;
    @NotBlank
    String name;

}

package edu.bbte.idde.lkim2156.spring.dto.incoming;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityInDto {
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @NotBlank(message = "Email address cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

}

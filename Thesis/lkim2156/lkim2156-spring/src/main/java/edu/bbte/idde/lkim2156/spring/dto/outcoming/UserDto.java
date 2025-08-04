package edu.bbte.idde.lkim2156.spring.dto.outcoming;

import edu.bbte.idde.lkim2156.spring.enums.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String token;
    private Role role;
}

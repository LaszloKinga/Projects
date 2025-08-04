package edu.bbte.idde.lkim2156.spring.dto.outcoming;

import edu.bbte.idde.lkim2156.spring.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityOutDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private Role role;
}

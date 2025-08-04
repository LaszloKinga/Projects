package edu.bbte.idde.lkim2156.spring.dto.incoming;

import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import edu.bbte.idde.lkim2156.spring.model.Webshop;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Collection;

@Data
public class StoreInDto {
    @NotBlank(message = "Address name cannot be blank")
    private String storeName;

    @NotBlank(message = "Address name cannot be blank")
    private String storeAddress;


    private Collection<Webshop> catalogEntry;

    private UserEntity userId;

}

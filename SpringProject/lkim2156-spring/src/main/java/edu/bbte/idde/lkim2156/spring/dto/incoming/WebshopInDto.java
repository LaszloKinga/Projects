package edu.bbte.idde.lkim2156.spring.dto.incoming;

import edu.bbte.idde.lkim2156.spring.model.Store;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebshopInDto {

    @NotNull(message = "Order date cannot be null")
    private LocalDate orderDate;

    @NotBlank(message = "Address name cannot be blank")
    private String address;

    @NotNull(message = "Total amount cannot be null")
    private double totalAmount;

    @NotBlank(message = "Payment method name cannot be blank")
    private String paymentMethod;

    private boolean isShipped;


    private Store storeId;
}

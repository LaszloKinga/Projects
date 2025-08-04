package edu.bbte.idde.lkim2156.spring.dto.outcoming;

import edu.bbte.idde.lkim2156.spring.model.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebshopOutDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private LocalDate orderDate;
    private String address;
    private double totalAmount;
    private String paymentMethod;
    private boolean isShipped;
    private Store storeId;
}

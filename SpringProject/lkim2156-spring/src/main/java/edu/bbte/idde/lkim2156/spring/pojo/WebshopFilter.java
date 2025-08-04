package edu.bbte.idde.lkim2156.spring.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebshopFilter implements Serializable {

    private static final long serialVersionUID = 1L;
    private Double minTotalAmount;
    private Double maxTotalAmount;
    private String paymentMethod;
    private Boolean shipped;
    private Integer storeId;
}

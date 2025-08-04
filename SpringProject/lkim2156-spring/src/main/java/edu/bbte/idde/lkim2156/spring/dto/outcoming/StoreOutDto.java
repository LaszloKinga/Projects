package edu.bbte.idde.lkim2156.spring.dto.outcoming;

import lombok.Data;

import java.util.Collection;

@Data
public class StoreOutDto {
    private Integer id;
    private String storeName;
    private String storeAddress;
    private Collection<WebshopOutDto> catalogEntry;
    private UserIdOutDto userId;

}

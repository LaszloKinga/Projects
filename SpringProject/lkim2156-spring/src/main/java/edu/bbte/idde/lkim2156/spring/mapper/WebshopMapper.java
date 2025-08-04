package edu.bbte.idde.lkim2156.spring.mapper;

import edu.bbte.idde.lkim2156.spring.dto.incoming.WebshopInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.WebshopOutDto;
import edu.bbte.idde.lkim2156.spring.model.Webshop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface WebshopMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "storeId", source = "storeId")
    Webshop mapRequestDtoToEntity(WebshopInDto webshopRequestDTO);

    WebshopOutDto mapEntityToResponseDto(Webshop webshop);
}

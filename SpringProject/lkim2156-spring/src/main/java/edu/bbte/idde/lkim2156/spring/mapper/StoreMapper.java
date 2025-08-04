package edu.bbte.idde.lkim2156.spring.mapper;

import edu.bbte.idde.lkim2156.spring.dto.incoming.StoreInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.StoreOutDto;
import edu.bbte.idde.lkim2156.spring.model.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface StoreMapper {

    @Mapping(target = "id", ignore = true)
    Store mapRequestDtoToEntity(StoreInDto storeRequestDTO);

    StoreOutDto mapEntityToResponseDto(Store store);

}

package edu.bbte.idde.lkim2156.spring.mapper;

import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserOutDto;
import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(source = "id", target = "id")
    UserOutDto mapEntityToDto(UserEntity userEntity);

}

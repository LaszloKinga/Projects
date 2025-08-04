package edu.bbte.idde.lkim2156.spring.mapper;

import edu.bbte.idde.lkim2156.spring.dto.incoming.SignUpDto;
import edu.bbte.idde.lkim2156.spring.dto.incoming.UserEntityInDto;
import edu.bbte.idde.lkim2156.spring.dto.incoming.UserEntityUpdateInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserEntityOutDto;
import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity mapRequestDtoToEntity(UserEntityInDto userEntityRequestDTO);

    UserEntityOutDto mapEntityToResponseUserEntityDto(UserEntity userEntity);

    UserDto toUserDto(UserEntity user);

    UserEntity fromUserEntityUpdateInDto(UserEntityUpdateInDto userEntityUpdateInDto);

    @Mapping(target = "password", ignore = true)
    UserEntity signUpToUser(SignUpDto signUpDto);


}

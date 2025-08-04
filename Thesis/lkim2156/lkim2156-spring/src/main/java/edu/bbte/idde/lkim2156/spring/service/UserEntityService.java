package edu.bbte.idde.lkim2156.spring.service;

import edu.bbte.idde.lkim2156.spring.dto.incoming.CredentialsDto;
import edu.bbte.idde.lkim2156.spring.dto.incoming.SignUpDto;
import edu.bbte.idde.lkim2156.spring.dto.incoming.UserEntityInDto;
import edu.bbte.idde.lkim2156.spring.dto.incoming.UserEntityUpdateInDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserDto;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserEntityOutDto;
import edu.bbte.idde.lkim2156.spring.enums.Role;
import edu.bbte.idde.lkim2156.spring.exception.AppException;
import edu.bbte.idde.lkim2156.spring.mapper.UserEntityMapper;
import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import edu.bbte.idde.lkim2156.spring.repository.EntityNotFoundException;
import edu.bbte.idde.lkim2156.spring.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserEntityService {

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private UserEntityMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntityOutDto createUserEntity(UserEntityInDto userInDto) {
        UserEntity userEntity = userMapper.mapRequestDtoToEntity(userInDto);
        userEntity.setRole(Role.USER);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(userEntity.getPassword())));
        log.info("service: {}", userEntity);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.mapEntityToResponseUserEntityDto(savedUserEntity);
    }


    public Collection<UserEntityOutDto> getUserEntities() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::mapEntityToResponseUserEntityDto)
                .collect(Collectors.toList());
    }


    public UserEntityOutDto getUserEntityById(Long id) {
        UserEntity userTmp = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found"));

        UserEntityOutDto user = userMapper.mapEntityToResponseUserEntityDto(userTmp);
        if (user == null) {
            throw new EntityNotFoundException("Error");
        } else {
            return user;
        }
    }

    public UserEntity getTotalUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }


    public UserEntityOutDto updateUserEntity(Long id, UserEntityUpdateInDto userEntityInDto) {
        log.info("Service PUT update started, userId={}", id);

        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with the given ID: " + id);
        }

        UserEntity existingUser = optionalUser.get();

        existingUser.setFirstName(userEntityInDto.getFirstName());
        existingUser.setLastName(userEntityInDto.getLastName());
        existingUser.setUserName(userEntityInDto.getUserName());
        existingUser.setEmail(userEntityInDto.getEmail());
        existingUser.setUpdatedAt(LocalDateTime.now());

        try {
            UserEntity updatedUser = userRepository.saveAndFlush(existingUser);
            log.info("Service PUT update successful: {}", updatedUser);
            return userMapper.mapEntityToResponseUserEntityDto(updatedUser);
        } catch (DataAccessException e) {
            log.error("Database error occurred while updating the user: {}", e.getMessage(), e);
            throw new IllegalStateException("User update failed due to database error.");
        }
    }


    public void deleteUserEntity(Long id) {
        userRepository.deleteById(id);
    }


    public UserDto login(CredentialsDto credentialsDto) {
        UserEntity user = userRepository.findByEmail(credentialsDto.email())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userDto.email());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        user.setRole(Role.USER);
        UserEntity savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserEntity findByEmailUserEntity(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

    }

}

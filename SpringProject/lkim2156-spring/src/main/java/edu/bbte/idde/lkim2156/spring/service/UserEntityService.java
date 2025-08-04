package edu.bbte.idde.lkim2156.spring.service;

import edu.bbte.idde.lkim2156.spring.dao.jpa.UserRepository;
import edu.bbte.idde.lkim2156.spring.dto.outcoming.UserOutDto;
import edu.bbte.idde.lkim2156.spring.mapper.UserEntityMapper;
import edu.bbte.idde.lkim2156.spring.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserEntityService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private UserRepository userRepository;

    public List<UserOutDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userEntityMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public UserOutDto createUser(UserEntity user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return userEntityMapper.mapEntityToDto(user);

    }
}

package com.shipon.moblileappws.service.impl;

import com.shipon.moblileappws.UserRepo;
import com.shipon.moblileappws.io.entity.UserEntity;
import com.shipon.moblileappws.service.UserService;
import com.shipon.moblileappws.shared.dto.UserDto;
import com.shipon.moblileappws.shared.dto.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo repo;
    @Autowired
    Utils utils;
    @Override
    public UserDto createUser(UserDto user) {
        UserEntity storedUser = repo.findUserEntityByEmail(user.getEmail());
        if (storedUser!= null) throw new RuntimeException("already exist");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        String publicUserId= utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword("1234");
        UserEntity savedUser=repo.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser,returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String id) {
        UserDto returnValue = new UserDto();
        UserEntity user=repo.findByUserId(id);
        BeanUtils.copyProperties(user,returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        UserEntity user=repo.findByUserId(id);

        if(user ==null) throw new RuntimeException();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        UserEntity userEntity=repo.save(user);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);
        return  returnValue;
    }

    @Override
    public void deleteUser(String id) {
        UserEntity userEntity =repo.findByUserId(id);
        if(userEntity ==null) throw new RuntimeException();

        repo.delete(userEntity);

    }

//    @Override
//    public List<UserDto> getUsers(int page, int limit) {
//        List<UserDto> returnValue =new ArrayList<>();
//
//        Pageable pageable= (Pageable) PageRequest.of(page, limit);
//        Page<UserEntity> userPage=repo.findAll((org.springframework.data.domain.Pageable) pageable);
//        List<UserEntity> users=userPage.getContent();
//
//        for (UserEntity userEntity : users) {
//            UserDto userDto = new UserDto();
//            BeanUtils.copyProperties(userEntity, userDto);
//            returnValue.add(userDto);
//        }
//
//        return returnValue;
//    }
}

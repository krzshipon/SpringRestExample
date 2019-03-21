package com.shipon.moblileappws.service;

import com.shipon.moblileappws.shared.dto.UserDto;

import java.util.List;

public interface UserService {
   UserDto createUser(UserDto user);

    UserDto getUserByUserId(String id);

    UserDto updateUser(String id,UserDto userDto);

    void deleteUser(String id);

//    List<UserDto> getUsers(int page, int limit);
}

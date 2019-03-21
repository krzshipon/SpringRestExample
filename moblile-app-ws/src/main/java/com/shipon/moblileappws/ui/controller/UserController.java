package com.shipon.moblileappws.ui.controller;

import com.shipon.moblileappws.service.UserService;
import com.shipon.moblileappws.shared.dto.UserDto;
import com.shipon.moblileappws.ui.model.request.UserDetailsRequestModel;
import com.shipon.moblileappws.ui.model.response.OperationStatusModel;
import com.shipon.moblileappws.ui.model.response.ReqOpStatus;
import com.shipon.moblileappws.ui.model.response.RequestOperationName;
import com.shipon.moblileappws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping(path="/{id}", produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();
        UserDto userDto=userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto,returnValue);
        return returnValue;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel){
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel,userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);
        return returnValue;
    }


    @PutMapping(path = "/{id}",
            consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetailsRequestModel){
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel,userDto);

        UserDto createdUser = userService.updateUser(id,userDto);
        BeanUtils.copyProperties(createdUser,returnValue);
        return returnValue;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOpName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);
        returnValue.setOpResult(ReqOpStatus.SUCCESS.name());

        return returnValue;
    }

//    @GetMapping( produces = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
//    )
//    public List<UserRest> getUsers(@RequestParam(value = "page",defaultValue = "1") int page,
//                                   @RequestParam(value = "limit",defaultValue = "25")int limit){
//        List<UserRest> userRestList = new ArrayList<>();
//        List<UserDto> users=userService.getUsers(page,limit);
//
//        for (UserDto userDto : users) {
//            UserRest userModel = new UserRest();
//            BeanUtils.copyProperties(userDto, userModel);
//            userRestList.add(userModel);
//        }
//        return userRestList;
//    }
}

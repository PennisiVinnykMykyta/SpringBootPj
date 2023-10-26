package com.nikita.springbootpj.controllers;


import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("getUser/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id){
        UserDTO userDTO = userService.getUserById(id);
        if(userDTO != null){
            return  new ResponseEntity(userDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userList")
    public ResponseEntity<UserDTO> getAllUsers(){
        List<UserDTO> usersList = userService.getAllUsers();
        if(usersList != null){
            return new ResponseEntity(usersList,HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/addOrUpdateUser", method = {RequestMethod.PUT, RequestMethod.POST})
    public void addOrUpdateUser(@Valid @RequestBody UserDTO userDTO) throws ParseException {
        userService.saveOrUpdateUser(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") int id){
        userService.deleteUserById(id);
    }




}

package com.nikita.springbootpj.controllers;


import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("get-user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id){
        UserDTO userDTO = userService.getUserById(id);
        if(userDTO != null){
            return  new ResponseEntity<>(userDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("verify-user/{email}")
    public ResponseEntity<UserDTO> getUserByCredentials(@PathVariable("email") String email){
        UserDTO userDTO = userService.getUserByCredentials(email);
        if(userDTO != null){
            return  new ResponseEntity<>(userDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user-list")
    public ResponseEntity<UserDTO> getAllUsers(){
        List<UserDTO> usersList = userService.getAllUsers();
        if(usersList != null){
            return new ResponseEntity(usersList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add-or-update-user", method = {RequestMethod.PUT, RequestMethod.POST})
    public void addOrUpdateUser(@RequestBody UserDTO userDTO) throws ParseException {
        userService.saveOrUpdateUser(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") int id){
       userService.deleteUserById(id);
    }




}

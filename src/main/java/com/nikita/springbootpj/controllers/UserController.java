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

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") int id){
        UserDTO userDTO = userService.getUserById(id);
        if(userDTO != null){
            return  new ResponseEntity<>(userDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/verify/{email}")
    public ResponseEntity<UserDTO> getUserByCredentials(@PathVariable("email") String email){
        UserDTO userDTO = userService.getUserByCredentials(email);
        if(userDTO != null){
            return  new ResponseEntity<>(userDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/list")
    public ResponseEntity<UserDTO> getAllUsers(){
        List<UserDTO> usersList = userService.getAllUsers();
        if(usersList != null){
            return new ResponseEntity(usersList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add-or-update", method = {RequestMethod.PUT, RequestMethod.POST})
    public void addOrUpdateUser(@RequestBody UserDTO userDTO) throws ParseException {
       userService.saveOrUpdateUser(userDTO);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable("userId") int id){
       userService.deleteUserById(id);
    }




}

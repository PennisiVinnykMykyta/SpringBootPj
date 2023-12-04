package com.nikita.springbootpj.controllers;


import com.nikita.springbootpj.dto.DownloadImageResponse;
import com.nikita.springbootpj.dto.UserAuthDTO;
import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.dto.UserDetailsDTO;
import com.nikita.springbootpj.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{email}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("email") String email){
        UserDTO userDTO = userService.getUserByEmail(email);
        if(userDTO != null){
            return  new ResponseEntity<>(userDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/profile-pic/upload/{userId}")
    public void uploadImage(@RequestPart("imageFile")MultipartFile file,@PathVariable int userId) throws IOException {
        userService.uploadProfilePic(file,userId);
    }

    @GetMapping(value = "/profile-pic/download/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DownloadImageResponse> downloadImage(@PathVariable int userId) throws IOException{

        DownloadImageResponse downloadImageResponse = userService.downloadProfilePic(userId);
        return new ResponseEntity<>(downloadImageResponse,HttpStatus.OK);
    }


    @PostMapping("/auth")
    public UserAuthDTO authentication(@RequestBody UserDetailsDTO userDetailsDTO){
        return this.userService.authenticate(userDetailsDTO.getEmail(),userDetailsDTO.getPassword());
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

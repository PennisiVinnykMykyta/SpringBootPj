package com.nikita.springbootpj.services.implementations;
import com.nikita.springbootpj.config.security.JwtProvider;
import com.nikita.springbootpj.dto.DownloadImageResponse;
import com.nikita.springbootpj.dto.UserAuthDTO;
import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.entities.enums.UserType;
import com.nikita.springbootpj.mappers.UserMapper;
import com.nikita.springbootpj.repositories.UserRepository;
import com.nikita.springbootpj.services.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${profile-folder.path}")
    private String folderPath;

   public void uploadProfilePic(MultipartFile file,int userId) throws IOException{

       Optional<User> optionalUser = userRepository.findById(userId);

       if(optionalUser.isPresent()){
           User user = optionalUser.get();

           //if the user already has a profile pic then we delete it and put a new one in the folder
           if(user.getProfilePicName() != null){
               File currentImage = new File(folderPath+userId+user.getProfilePicName());
               if(!currentImage.delete()){
                   throw new IOException("Couldn't delete Profile Pic");
               }
           }

           user.setProfilePicName(file.getOriginalFilename());
           userRepository.save(user);

           file.transferTo(new File(folderPath+userId+file.getOriginalFilename()));
       }


    }

   public DownloadImageResponse downloadProfilePic(int userId) throws IOException {
       Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String path = user.getProfilePicName();
            if (path != null) {

                String imageType = path.substring(path.lastIndexOf('.')+1);

                byte[] fileContent = FileUtil.readAsByteArray(new File(folderPath+userId+path));
                String encodedImage = Base64.getEncoder().encodeToString(fileContent);

                DownloadImageResponse downloadImageResponse = new DownloadImageResponse();
                downloadImageResponse.setImage(encodedImage);
                downloadImageResponse.setContentType(imageType);

                return downloadImageResponse;
            }
        }
        return null;
    }

    public UserDTO getUserById(int id){
        UserDTO userDTO = null;
        if(userRepository.findById(id).isPresent()){
            userDTO = userMapper.fromUserToDTO(userRepository.findById(id).get());
        }
        return userDTO;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        if(email != null ){
            return userMapper.fromUserToDTO(userRepository.getUserByEmail(email));
        }
        return null;
    }

    public List<UserDTO> getAllUsers(){
        List<User> userList = userRepository.findAll();
        List<UserDTO> users = new ArrayList<>();
        for(User user : userList){
            users.add(userMapper.fromUserToDTO(user));
        }
        return users;
    }

    public void saveOrUpdateUser(UserDTO userDTO){
        if(userDTO.getId() == null){
            userRepository.save(userMapper.fromDtoToUser(userDTO));

        }else{
            Optional<User> optionalUser = userRepository.findById(userDTO.getId());
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                userMapper.updateUser(user,userDTO);
                userRepository.save(user);
            }
        }
    }

    public void deleteUserById(int id){
        UserDTO userDTO = this.getUserById(id);
        if(userDTO != null){
            userRepository.deleteById(userDTO.getId());
        }
    }

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserAuthDTO authenticate(String email, String password){
        User user = this.userRepository.getUserByEmail(email);

        //admin@admin.com
        //admin

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not valid credentials");
        }

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        Map<String,Object> claimMap= new HashMap<>(0);
        if(user.getType() == UserType.ADMIN){
            claimMap.put("userType","ADMIN");
            userAuthDTO.setUserType("ADMIN");
        }else{
            claimMap.put("userType","CUSTOMER");
            userAuthDTO.setUserType("CUSTOMER");
        }

        String jwt = jwtProvider.generateToken(claimMap,email);
        claimMap.put("token",jwt);

        userAuthDTO.setToken(jwt);
        return userAuthDTO;

    }
}

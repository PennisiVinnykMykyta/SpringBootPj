package com.nikita.springbootpj.services.implementations;
import com.nikita.springbootpj.config.security.JwtProvider;
import com.nikita.springbootpj.dto.UserAuthDTO;
import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.entities.enums.UserType;
import com.nikita.springbootpj.mappers.UserMapper;
import com.nikita.springbootpj.repositories.UserRepository;
import com.nikita.springbootpj.services.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.FileUtil;
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
    private static final String folderPath = "C:/Users/Nikit/Desktop/Lavoro/Proggetti/Proggetti Completi/SpringBootPj/src/main/resources/ProfilePics/";

   public void uploadProfilePic(MultipartFile file) throws IOException{

       //extract the user id from the multifile and save the imageName to user
       int endIndex = file.getOriginalFilename().indexOf("_");
       int userId = Integer.parseInt(file.getOriginalFilename().substring(0,endIndex));
       User user = userRepository.findById(userId).get();

       //if the user already has a profile pic then we delete it and put a new one in the folder
       if(user.getProfilePicName() != null){
           File currentImage = new File(folderPath+user.getProfilePicName());
           currentImage.delete();
       }

       user.setProfilePicName(file.getOriginalFilename());
       userRepository.save(user);

       file.transferTo(new File(folderPath+file.getOriginalFilename()));
    }

   public String downloadProfilePic(int userId) throws IOException {
       Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            String path = user.get().getProfilePicName();
            if (path != null) {

                byte[] fileContent = FileUtil.readAsByteArray(new File(folderPath+path));
                String encodedImage = Base64.getEncoder().encodeToString(fileContent);
                return encodedImage;
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

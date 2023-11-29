package com.nikita.springbootpj.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageInfoDTO {
    private MultipartFile image;
    private int userId;
}

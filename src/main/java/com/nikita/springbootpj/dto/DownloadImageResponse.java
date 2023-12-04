package com.nikita.springbootpj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadImageResponse {
    private String image;
    private String contentType;
}

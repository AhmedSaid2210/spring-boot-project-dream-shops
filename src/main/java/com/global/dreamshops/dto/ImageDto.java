package com.global.dreamshops.dto;

import com.global.dreamshops.model.Product;
import lombok.*;

import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private Long imageId;
    private String imageName;
    private String downloadUrl;


}

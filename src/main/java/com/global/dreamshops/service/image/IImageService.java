package com.global.dreamshops.service.image;

import com.global.dreamshops.dto.ImageDto;
import com.global.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    List<Image> getAllImages();
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    Image updateImage(MultipartFile file,Long imageId);
    void deleteImageById(Long id);
}

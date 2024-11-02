package com.global.dreamshops.service.image;

import com.global.dreamshops.dto.ImageDto;
import com.global.dreamshops.exception.ImageNotFoundException;
import com.global.dreamshops.model.Image;
import com.global.dreamshops.model.Product;
import com.global.dreamshops.repo.image.ImageRepo;
import com.global.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepo imageRepo;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found"));
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepo.findAll();
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                image.setProduct(product);
                Image savedImage = imageRepo.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepo.save(savedImage);
                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImages.add(imageDto);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
        return savedImages;
    }

    @Override
    public Image updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteImageById(Long id) {
        Optional.ofNullable(imageRepo.findById(id).get())
                .ifPresentOrElse(imageRepo::delete, () -> {throw new ImageNotFoundException("Image not found");});

    }
}

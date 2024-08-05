package first.second.third.fuckmylife.service;

import first.second.third.fuckmylife.Entity.PreviewImage;

import java.util.List;

public interface ImagesService {
    void saveImage(PreviewImage previewImage);
    PreviewImage findImageById(Long id);
    List<PreviewImage> findAllImages();
    void deleteImage(PreviewImage previewImage);
}
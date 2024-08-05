package first.second.third.fuckmylife.dao;

import first.second.third.fuckmylife.Entity.PreviewImage;

import java.util.List;

public interface PreviewImageDao {
    void save(PreviewImage previewImage);
    PreviewImage findById(Long id);
    List<PreviewImage> findAll();
    void delete(PreviewImage previewImage);
}
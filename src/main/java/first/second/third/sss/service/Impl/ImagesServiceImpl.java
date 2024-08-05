package first.second.third.fuckmylife.service.Impl;

import first.second.third.fuckmylife.Entity.PreviewImage;
import first.second.third.fuckmylife.dao.PreviewImageDao;
import first.second.third.fuckmylife.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private PreviewImageDao previewImageDao;

    @Override
    @Transactional
    public void saveImage(PreviewImage previewImage) {
        previewImageDao.save(previewImage);
    }

    @Override
    @Transactional
    public PreviewImage findImageById(Long id) {
        return previewImageDao.findById(id);
    }

    @Override
    @Transactional
    public List<PreviewImage> findAllImages() {
        return previewImageDao.findAll();
    }

    @Override
    @Transactional
    public void deleteImage(PreviewImage previewImage) {
        previewImageDao.delete(previewImage);
    }
}
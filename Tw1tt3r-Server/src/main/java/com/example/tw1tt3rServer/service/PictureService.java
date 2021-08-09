package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.aspects.NoLogging;
import com.example.tw1tt3rServer.repository.PictureRepository;
import com.example.tw1tt3rServer.repository.entity.Picture;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PictureService {
    @Autowired
    PictureRepository pictureRepository;

    public Picture save(Picture picture){
        return(pictureRepository.save(picture));
    }

    public void delete(Picture picture){
        pictureRepository.delete(picture);
    }

    @SneakyThrows
    public Picture makePicture(MultipartFile multipartFile){
        Picture picture = new Picture();
        picture.setContent(multipartFile.getBytes());
        return(save(picture));
    }

    @NoLogging
    public Picture findById(int id){
        if(!pictureRepository.existsById(id)){
            return(null);
        }
        return(pictureRepository.findById(id).get());
    }

}























package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.repository.PictureRepository;
import com.example.tw1tt3rServer.repository.entity.Picture;
import dtos.PictureDto;
import javafx.scene.image.Image;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utility.ModelMapperInstance;

import java.io.ByteArrayInputStream;

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
    public Picture makePicture(PictureDto pictureDto){
        if(pictureDto.getContent()==null){
            return(null);
        }
        Picture picture = ModelMapperInstance.getModelMapper().map(pictureDto, Picture.class);
        return(save(picture));
    }

    @SneakyThrows
    public Image getImageType(Picture picture){
        if(picture==null){
            return(null);
        }
        if(picture.getContent()==null){
            return(null);
        }
        return(new Image(new ByteArrayInputStream(picture.getContent())));
    }
}























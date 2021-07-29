package com.example.tw1tt3rServer.service;

import com.example.tw1tt3rServer.repository.PictureRepository;
import com.example.tw1tt3rServer.repository.entity.Picture;
import javafx.scene.image.Image;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

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
    public Picture makePicture(File file){
        Picture picture = new Picture();
        byte[] bt = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(bt);
        picture.setContent(bt);
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























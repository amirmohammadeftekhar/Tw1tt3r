package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Picture;
import dtos.PictureDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utility.ModelMapperInstance;

@RestController
public class PictureServiceServerController extends AbstractServerController {
    @PostMapping("api/pictureservice/makepicture")
    public PictureDto makePicture(@RequestBody PictureDto picture){
        Picture pic = pictureService.makePicture(picture);
        return(ModelMapperInstance.getModelMapper().map(pic, PictureDto.class));
    }
}

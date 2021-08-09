package com.example.tw1tt3rServer.controller.services;

import com.example.tw1tt3rServer.controller.AbstractServerController;
import com.example.tw1tt3rServer.repository.entity.Picture;
import dtos.PictureDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utility.ModelMapperInstance;

@RestController
public class PictureServiceServerController extends AbstractServerController {
    @PostMapping("api/pictureservice/makepicture")
    public PictureDto makePicture(@RequestParam("file") MultipartFile file){
        System.out.println("!!!");
        Picture pic = pictureService.makePicture(file);
        return(ModelMapperInstance.getModelMapper().map(pic, PictureDto.class));
    }

    @GetMapping("api/pictureservice/getpicture")
    public @ResponseBody byte[] getPicture(@RequestParam int pictureId){
        Picture picture = pictureService.findById(pictureId);
        return(picture.getContent());
    }
}

package com.example.tw1tt3rServer.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Picture {

    public Picture(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    byte[] content;
}

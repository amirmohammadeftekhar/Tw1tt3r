package com.example.tw1tt3rServer.repository;

import com.example.tw1tt3rServer.repository.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
}

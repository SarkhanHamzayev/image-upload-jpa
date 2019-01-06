package com.example.imageuploadjpa.resource;

import com.example.imageuploadjpa.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageResource extends JpaRepository<Image,Integer> {


    Optional<Image> findById(Long id);
}

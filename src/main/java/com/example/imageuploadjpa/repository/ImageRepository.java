package com.example.imageuploadjpa.repository;


import com.example.imageuploadjpa.model.Image;
import com.example.imageuploadjpa.util.ImagePath;
import com.example.imageuploadjpa.resource.ImageResource;
import com.example.imageuploadjpa.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageRepository {


    ImageResource imageResource;

    @Autowired
    public ImageRepository(ImageResource imageResource) {
        this.imageResource = imageResource;
    }

    @GetMapping("/all")
    public List<Image> getImageList() {
        return imageResource.findAll();
    }

    @PostMapping("/load")
    public List<Image> addImage(@RequestParam MultipartFile image) throws IOException {
        String imagePath = "";
        if (image != null) {
            imagePath = ImagePath.deploy(image);
            if (imagePath.equals("")) {
                return null;
            }
            Image image1 = new Image();
            image1.setImagePath(imagePath);
            image1.setView(1);
            imageResource.save(image1);
            return imageResource.findAll();
        } else {
            return null;
        }
    }

    @PutMapping("/update")
    public Image updateNote(@RequestParam("id") int id,
                            @RequestParam("image") MultipartFile image) throws IOException {
        String imagePath = "";
        if (image != null) {
            imagePath = ImagePath.deploy(image);
            if (imagePath.equals("")) {
                return null;
            }
            Image image1 = new Image();
            image1.setId(id);
            image1.setImagePath(imagePath);
            image1.setView(1);
            imageResource.save(image1);
            Image updatedImage = imageResource.save(image1);
            return updatedImage;

        } else {
            return null;
        }

    }

    @GetMapping("/detail/{id}")
    public Image getNoteById(@PathVariable(value = "id") Long Id) {
        return imageResource.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Image", "id", Id));
    }


    @PostMapping("/delete/{id}")
    public void deleteImageById(@PathVariable("id") int id) {

        imageResource.deleteById(id);
    }

    @RequestMapping(value = "/downloadFile/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") int id) throws IOException {
//        Optional<Image> image = imageResource.findById(id);
        InputStream inputImage = new FileInputStream("C:\\Users\\Sarkhan\\Desktop\\downljoadasd.jpg");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int l = inputImage.read(buffer);
        while(l >= 0) {
            outputStream.write(buffer, 0, l);
            l = inputImage.read(buffer);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");
        headers.set("Content-Disposition", "attachment; filename=\"" + "das" + ".jpg\"");
        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }


}

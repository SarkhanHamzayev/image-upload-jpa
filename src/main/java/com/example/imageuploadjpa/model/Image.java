package com.example.imageuploadjpa.model;

import javax.persistence.*;

@Entity
@Table(name = "image",catalog = "test")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "imagepath")
    private String imagePath;
    @Column(name = "view")
    private int view;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }


}
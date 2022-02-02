package com.example.firebasestoragewithuploadimage.Modelclass;



public class ImageUpload {
    private String ImageURI;

    public ImageUpload(String imageURI) {
        ImageURI = imageURI;
    }

    public ImageUpload() {
    }

    public String getImageURI() {
        return ImageURI;
    }

    public void setImageURI(String imageURI) {
        ImageURI = imageURI;
    }
}

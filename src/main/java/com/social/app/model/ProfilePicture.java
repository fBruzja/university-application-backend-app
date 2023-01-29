package com.social.app.model;

public class ProfilePicture {
    private String base64Image;

    // for deserialization
    public ProfilePicture() {
    }

    public ProfilePicture(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}

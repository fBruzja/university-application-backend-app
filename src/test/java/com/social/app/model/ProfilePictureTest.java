package com.social.app.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProfilePictureTest {

    @Test
    public void testGetBase64Image() {
        String base64Image = "abc123";
        ProfilePicture picture = new ProfilePicture(base64Image);
        assertEquals(base64Image, picture.getBase64Image());
    }

    @Test
    public void testSetBase64Image() {
        String base64Image = "def456";
        ProfilePicture picture = new ProfilePicture();
        picture.setBase64Image(base64Image);
        assertEquals(base64Image, picture.getBase64Image());
    }
}

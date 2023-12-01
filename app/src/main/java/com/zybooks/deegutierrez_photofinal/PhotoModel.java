package com.zybooks.deegutierrez_photofinal;

public class PhotoModel {
    private byte[] photoData;
    private String photoTag;

    public PhotoModel(byte[] photoData, String photoTag) {
        this.photoData = photoData;
        this.photoTag = photoTag;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public String getPhotoTag() {
        return photoTag;
    }
}

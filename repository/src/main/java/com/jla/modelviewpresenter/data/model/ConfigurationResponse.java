package com.jla.modelviewpresenter.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ConfigurationResponse {

    private static final String ID = "id";
    private static final String IMAGES = "images";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(foreign = true, columnName = IMAGES)
    private ImagesResponse images;

    public ConfigurationResponse() {
    }

    public ConfigurationResponse(ImagesResponse images) {
        this.images = images;
    }

    public ImagesResponse getImages() {
        return images;
    }

    public void setImages(ImagesResponse images) {
        this.images = images;
    }
}

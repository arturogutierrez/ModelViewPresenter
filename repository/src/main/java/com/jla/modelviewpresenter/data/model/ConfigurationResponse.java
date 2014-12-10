package com.jla.modelviewpresenter.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class ConfigurationResponse {

    private static final String ID = "id";
    private static final String IMAGES = "images";
    private static final String UPDATED_AT = "updatedAt";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(foreign = true, columnName = IMAGES)
    private ImagesResponse images;
    @DatabaseField(columnName = UPDATED_AT)
    private Date updated_at;

    public ConfigurationResponse() {
    }

    public ConfigurationResponse(ImagesResponse images, Date updatedAt) {
        this.images = images;
        this.updated_at = updatedAt;
    }

    public ImagesResponse getImages() {
        return images;
    }

    public void setImages(ImagesResponse images) {
        this.images = images;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updated_at = updatedAt;
    }
}

package com.roblebob.ud801_bakingapp.model;

import androidx.annotation.NonNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Step {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }
    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }


    @Override
    public boolean equals(Object o) {
        //if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step that = (Step) o;
        //return id == that.id  &&  Objects.equal(shortDescription, that.shortDescription)  &&  Objects.equal(description, that.description)  &&  Objects.equal(videoURL, that.videoURL)  &&  Objects.equal(thumbnailURL, that.thumbnailURL);
        return hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
        //return Objects.hashCode(id, shortDescription, description, videoURL, thumbnailURL);
    }

    @NonNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("shortDescription", shortDescription)
                .add("description", description)
                .add("videoURL", videoURL)
                .add("thumbnailURL", thumbnailURL)
                .toString();
    }

}

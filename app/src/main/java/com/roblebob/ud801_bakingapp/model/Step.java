package com.roblebob.ud801_bakingapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "Step", indices = @Index(value = {"recipeName", "stepNumber"}, unique = true))
public class Step {

    @PrimaryKey(autoGenerate = true)        private int     id;
    @ColumnInfo(name = "recipeName")        private String  recipeName;
    @ColumnInfo(name = "stepNumber")        private int     stepNumber;
    @ColumnInfo(name = "shortDescription")  private String  shortDescription;
    @ColumnInfo(name = "description")       private String  description;
    @ColumnInfo(name = "videoURL")          private String  videoURL;
    @ColumnInfo(name = "thumbnailURL")      private String  thumbnailURL;

    public Step(String recipeName, int stepNumber, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.recipeName = recipeName;
        this.stepNumber = stepNumber;
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

    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getStepNumber() {
        return stepNumber;
    }
    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
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
    public int hashCode() {
        return Objects.hash( getRecipeName(), getStepNumber(), getShortDescription(), getDescription(), getVideoURL(), getThumbnailURL());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Step step = (Step) obj;
        return  this.getRecipeName().equals(step.getRecipeName())  &&
                this.getStepNumber() == step.getStepNumber()  &&
                this.getShortDescription().equals(step.getShortDescription())  &&
                this.getDescription().equals(step.getDescription())  &&
                this.getVideoURL().equals(step.getVideoURL())  &&
                this.getThumbnailURL().equals(step.getThumbnailURL());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apigalaxy.POJOs;

/**
 *
 * @author Fanch
 */
public class BuildType {
    
    private Integer buildType_id;
    private String name, description, image;

    public BuildType() {
    }

    public BuildType(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Integer getBuildType_id() {
        return buildType_id;
    }

    public void setBuildType_id(Integer buildType_id) {
        this.buildType_id = buildType_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "BuildType{" + "buildType_id=" + buildType_id + ", name=" + name + ", description=" + description + ", image=" + image + '}';
    }
    
    
}

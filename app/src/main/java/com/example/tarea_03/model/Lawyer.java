package com.example.tarea_03.model;

public class Lawyer {
    private String id;
    private String name;
    private String specialty;
    private String phone;
    private String bio;
    private String avatarUri;


    public Lawyer() {
    }

    public Lawyer(String id, String name, String specialty, String phone, String bio, String avatarUri) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.bio = bio;
        this.avatarUri = avatarUri;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }
}

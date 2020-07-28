package com.apps.gofindmovie.model;

import com.google.gson.annotations.SerializedName;

public class InfoActors {

    @SerializedName("birthday")
    private String birthday;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("biography")
    private String biography;
    @SerializedName("profile_path")
    private String profile_path;
    @SerializedName("place_of_birth")
    private String place_of_birth;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getProfile_path() {
        return "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }
}

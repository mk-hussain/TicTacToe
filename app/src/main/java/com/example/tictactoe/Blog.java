package com.example.tictactoe;

public class Blog {
    //     model class for each post
    private String title,description,image,UserName;

    public Blog(){
    }

    public Blog(String title, String description, String image, String userName) {
        this.title = title;
        this.description = description;
        this.image = image;
        UserName=userName;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", UserName='" + UserName + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName=userName;
    }
}

package com.example.tictactoe;

public class itemUserlist {
    private String Name;
    private int Photo;

    //constructor
    public itemUserlist(String name, int photo) {
        Name = name;
        Photo = photo;
    }

    public itemUserlist() {
    }

    //getter

    public String getName() {
        return Name;
    }

    public int getPhoto() {
        return Photo;
    }

    //setter


    public void setName(String name) {
        Name = name;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }
}

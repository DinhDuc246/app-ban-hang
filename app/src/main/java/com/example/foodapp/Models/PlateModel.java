package com.example.foodapp.Models;

public class PlateModel {
    public PlateModel(int plate_img){
        /// empty constructor///////
        this.plate_img = plate_img;
    }
    private int plate_img;

    public int getPlate_img() {
        return plate_img;
    }

    public void setPlate_img(int plate_img) {
        this.plate_img = plate_img;
    }
}

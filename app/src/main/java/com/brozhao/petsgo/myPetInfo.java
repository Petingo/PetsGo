package com.brozhao.petsgo;

/**
 * Created by petingo on 2017/7/14.
 */

public class MyPetInfo {
    private String name;
    private double foodCookie;
    private double foodCan;
    private double water;
    MyPetInfo(String name, double foodCookie, double foodCan){
        this.name = name;
        this.foodCookie = foodCookie;
        this.foodCan = foodCan;
        this.water = 0;
    }
    public String getName(){
        return this.name;
    }
    public double getFoodCookie(){
        return this.foodCookie;
    }
    public double getFoodCan(){
        return this.foodCan;
    }
    public double getWater() {return this.water;}


}

package com.strawberry.delivery.data;

import java.io.Serializable;

public class foodCounter implements Serializable {
    private String foodName;
    private int foodPrice;
    private int foodCount;
    public foodCounter(String n,int p,int c){
        this.foodName=n;
        this.foodPrice=p;
        this.foodCount=c;
    }
    public int getTotal(){
        return this.foodPrice*this.foodCount;
    }
    public String getName(){return this.foodName;}
    public int getPrice(){return this.foodPrice;}
    public int getCount(){return this.foodCount;}


}

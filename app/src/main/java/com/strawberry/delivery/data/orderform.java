package com.strawberry.delivery.data;

import com.strawberry.delivery.data.foodCounter;

import java.io.Serializable;
import java.util.List;

public class orderform implements Serializable {
    private String ID;
    private List<foodCounter> foods;
    private int total;
    private String targetAddress;
    private String restAddress;
    public orderform(String ID,List<foodCounter> foods,String tAddress,String rAddress){
        this.ID=ID;
        this.foods=foods;
        for(foodCounter f:foods){
            this.total+=f.getTotal();
        }
        this.targetAddress=tAddress;
        this.restAddress=rAddress;
    }

    public String getID() {
        return ID;
    }

    public List<foodCounter> getFoods() {
        return foods;
    }

    public int getTotal() {

        return total;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public String getRestAddress() {
        return restAddress;
    }
}

package com.bdlabit.shaqib.jubot.Model;

public class Order {
    private int ID;
    private String foodName;
    private String foodQuantity;
    private String foodPrice;

    public Order() {
    }

    public Order(String foodName, String foodQuantity, String foodPrice) {
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.foodPrice = foodPrice;
    }

    public Order(int ID, String foodName, String foodQuantity, String foodPrice) {
        this.ID = ID;
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.foodPrice = foodPrice;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }
}

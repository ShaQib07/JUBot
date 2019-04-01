package com.bdlabit.shaqib.jubot.Model;

public class Food {
    public String name;
    public String price_tk;
    public String availability;
    public String imageLink;

    public Food() {
    }

    public Food(String name, String price_tk, String availability) {
        this.name = name;
        this.price_tk = price_tk;
        this.availability = availability;
    }

    public Food(String name, String price_tk, String availability, String imageLink) {
        this.name = name;
        this.price_tk = price_tk;
        this.availability = availability;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice_tk() {
        return price_tk;
    }

    public void setPrice_tk(String price_tk) {
        this.price_tk = price_tk;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}

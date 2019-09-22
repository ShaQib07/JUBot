package com.bdlabit.shaqib.jubot.Model;

public class User {
    private String phone;
    private String password;
    private String name;
    private String dept;
    private String hall;
    private String room;
    private String batch;
    private String email;
    private String token;

    public User() { }

    public User(String phone, String password){
        this.phone = phone;
        this.password = password;
    }

    public User(String phone, String password, String name, String dept, String hall, String room, String batch) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.dept = dept;
        this.hall = hall;
        this.room = room;
        this.batch = batch;
    }

    public User(String phone, String password, String name, String dept, String hall, String room, String batch, String email) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.dept = dept;
        this.hall = hall;
        this.room = room;
        this.batch = batch;
        this.email = email;
    }

    public User(String phone, String password, String name, String dept, String hall, String room, String batch, String email, String token) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.dept = dept;
        this.hall = hall;
        this.room = room;
        this.batch = batch;
        this.email = email;
        this.token = token;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

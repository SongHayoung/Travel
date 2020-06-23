package com.Travel.biz.User.Domain;

public class User {
    String id;
    String pass;
    String name;
    String gender;
    String email;

    public User () {}

    public User(String id, String pass, String name, String gender, String email) {
        this.id = id;
        this.pass = pass;
        this.name = name;
        this.gender = gender;
        this.email = email;
    }

    public String getId() { return this.id; }
    public String getPass() { return this.pass; }
    public String getName() { return this.name; }
    public String getGender() { return this.gender; }
    public String getEmail() { return this.email; }

    public void setId(String id) { this.id = id; }
    public void setPass(String pass) { this.pass = pass; }
    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setEmail(String email) { this.email = email; }
}

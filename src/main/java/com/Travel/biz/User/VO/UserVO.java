package com.Travel.biz.User.VO;

public class UserVO {
    String id;
    String pass;
    String name;
    String gender;
    String email;
    String nickname;

    public UserVO() {}

    public UserVO(String id, String pass, String name, String gender, String email, String nickname) {
        this.id = id;
        this.pass = pass;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.nickname = nickname;
    }

    public String getId() { return this.id; }
    public String getPass() { return this.pass; }
    public String getName() { return this.name; }
    public String getGender() { return this.gender; }
    public String getEmail() { return this.email; }
    public String getNickname() { return this.nickname; }

    public void setId(String id) { this.id = id; }
    public void setPass(String pass) { this.pass = pass; }
    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setEmail(String email) { this.email = email; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}

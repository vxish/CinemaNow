package com.example.vi5h.cinemanow;

public class UserModel
{
        int id;
        String name;
        String username;
        String pass;
        String userType;

    public UserModel()
    {

    }

    public int getId() {
        return  this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserType() {return userType;}

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

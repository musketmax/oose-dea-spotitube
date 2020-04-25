package com.thomas.spotitube.domain;

import javax.xml.bind.annotation.XmlAttribute;

public class Credentials {
    private String user;
    private String password;

    public String getUser() { return user; }

    @XmlAttribute(name = "user")
    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    @XmlAttribute(name = "password")
    public void setPassword(String password) {
        this.password = password;
    }
}

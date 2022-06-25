package com.netdisk.baiguo.domain;

import lombok.Data;

@Data
public class User {
    private int id;
    private String eMail;
    private String dateTime;
    private String password;

    private int rootDirId;

    public User() {
    }

    public User(int id, String eMail, String dateTime, String password) {
        this.id = id;
        this.eMail = eMail;
        this.dateTime = dateTime;
        this.password = password;
    }
}

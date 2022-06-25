package com.netdisk.baiguo.domain;

import lombok.Data;

@Data
public class OperationLog {
    private int id;
    private int type;
    private String content;
    private String dateTime;
    private User user;

    public OperationLog() {
    }

    public OperationLog(int type, String content, User user) {
        this.type = type;
        this.content = content;
        this.user = user;
    }
}

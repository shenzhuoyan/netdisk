package com.netdisk.baiguo.domain;

import lombok.Data;

@Data
public class RecycleBin {
    private int id;
    private FileDir fileDir;
    private String delTime;
    private int userId;
}

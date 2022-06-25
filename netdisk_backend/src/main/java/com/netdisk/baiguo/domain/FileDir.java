package com.netdisk.baiguo.domain;

import lombok.Data;

import java.util.List;

@Data
public class FileDir {
    private int id;
    private String name;
    private int parentId;
    private int type;
    private int level;
    private User user;
    private String path;
    private String size;
    private String genPath;
    private String createTime;
}

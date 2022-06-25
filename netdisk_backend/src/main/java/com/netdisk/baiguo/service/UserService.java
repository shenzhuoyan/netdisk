package com.netdisk.baiguo.service;

import com.netdisk.baiguo.domain.User;

import java.io.IOException;

public interface UserService {
    boolean addUser(User user) throws IOException;

    boolean delUser(int id);

    User getUser(int id);

    boolean checkUserFile(int userId, int fileDirId);

    User getByEmail(String email);
}

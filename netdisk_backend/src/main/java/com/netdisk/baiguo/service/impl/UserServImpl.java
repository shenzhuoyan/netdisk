package com.netdisk.baiguo.service.impl;

import com.netdisk.baiguo.dao.FileDirDao;
import com.netdisk.baiguo.dao.UserDao;
import com.netdisk.baiguo.domain.FileDir;
import com.netdisk.baiguo.domain.User;
import com.netdisk.baiguo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserServImpl implements UserService {

    @Value("${file.rootFolder}")
    private String rootFolder;
    @Autowired
    private UserDao userDao;

    @Autowired
    private FileDirDao fileDirDao;

    @Override
    public boolean addUser(User user) {
        if (userDao.insertUser(user) == 1)
            try {
                Path path = Paths.get(rootFolder + user.getId());
                Files.createDirectory(path);
                FileDir fileDir = new FileDir();
                fileDir.setUser(user);
                fileDir.setName(String.valueOf(user.getId()));
                fileDir.setPath(user.getId() + "");
                return fileDirDao.insertRootFileDir(fileDir)==1;
            } catch (Exception Exception) {
                return false;
            }
        return false;
    }

    @Override
    public boolean delUser(int id) {
        return userDao.updateTo0(id) == 1;
    }

    @Override
    public User getUser(int id) {
        return userDao.selectUser(id);
    }

    @Override
    public boolean checkUserFile(int userId, int fileDirId) {
        FileDir fileDir = fileDirDao.selectById(fileDirId);
        return fileDir != null && fileDir.getUser().getId() == userId;
    }

    @Override
    public User getByEmail(String email) {
        return userDao.selectByEmail(email);
    }
}

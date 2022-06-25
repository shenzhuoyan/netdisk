package com.netdisk.baiguo.controller;

import com.netdisk.baiguo.domain.FileDir;
import com.netdisk.baiguo.domain.User;
import com.netdisk.baiguo.service.FileDirService;
import com.netdisk.baiguo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class FileDirController {

    @Autowired
    private FileDirService fileDirService;

    @Autowired
    private UserService userService;

    private StringRedisTemplate stringRedisTemplate;

    private boolean userNotHasFile(int userId, int fileDirId) {
        return !userService.checkUserFile(userId, fileDirId);
    }

    //增
    @RequestMapping("/addDir")
    public boolean addDir(@RequestBody FileDir fileDir, @CookieValue(name = "userId") int userId) {//要求上传父目录，目录名
        //添加文件和目录必须先验证父目录是不是自己的
        FileDir parent = fileDirService.getFile(fileDir.getParentId());
        if (userNotHasFile(userId, parent.getId()))
            return false;
        User user = new User();
        user.setId(userId);
        fileDir.setUser(user);
        return fileDirService.addDir(fileDir);
    }

    @PostMapping("/addFile")
    public boolean addFile(@RequestParam("parentId") int parentId, @RequestParam("file") MultipartFile file, @CookieValue(name = "userId") int userId) {
        //添加文件和目录必须先验证父目录是不是自己的
        FileDir parent = fileDirService.getFile(parentId);
        if (userNotHasFile(userId, parent.getId()))
            return false;
        FileDir fileDir = new FileDir();
        fileDir.setParentId(parentId);
        User user = new User();
        user.setId(userId);
        fileDir.setUser(user);
        return fileDirService.pushFile(fileDir, file);
    }

    //删
    @RequestMapping("/delDir")
    public boolean delDir(@RequestParam("dirId") int dirId, @CookieValue(name = "userId") int userId) {
        if (userNotHasFile(userId, dirId))
            return false;
        return fileDirService.delDir(dirId);
    }

    @RequestMapping("/delFile")
    public boolean delFile(@RequestParam("fileId") int fileId, @CookieValue(name = "userId") int userId) {
        if (userNotHasFile(userId, fileId))
            return false;
        return fileDirService.delFile(fileId);
    }

    //改
    //懒得写了，太麻烦


    //查
    @RequestMapping("/getDir")
    public List<FileDir> getDir(@RequestParam("dirId") int dirId, @CookieValue(name = "userId") int userId) {
        if (userNotHasFile(userId, dirId))
            return null;
        return fileDirService.getDir(dirId);
    }

    @RequestMapping("/getDirSelf")
    public FileDir getDirSelf(@RequestParam("dirId") int dirId, @CookieValue(name = "userId") int userId){
        if (userNotHasFile(userId, dirId))
            return null;
        return fileDirService.getFile(dirId);
    }
    @RequestMapping("/getRoot")
    public FileDir getRoot(@CookieValue(name = "userId") int userId) {
        return fileDirService.getRoot(userId);
    }

    @RequestMapping("/getFile")
    public FileDir getFile(@RequestParam("fileId") int fileId, @CookieValue(name = "userId") int userId) {
        if (userNotHasFile(userId, fileId))
            return null;
        return fileDirService.getFile(fileId);
    }


    @RequestMapping("/download/{userId}/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("userId") int userId, @PathVariable("fileName") String fileName, @CookieValue(name = "userId") int realUserId) {
        if (userId != realUserId) return null;
        return fileDirService.downloadFile(userId + "/" + fileName, fileName);
    }
}

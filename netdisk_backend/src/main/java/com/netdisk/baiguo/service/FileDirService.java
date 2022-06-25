package com.netdisk.baiguo.service;

import com.netdisk.baiguo.domain.FileDir;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface FileDirService {
    boolean addDir(FileDir fileDir);

    boolean pushFile(FileDir fileDir,MultipartFile picture);


    boolean delDir(int dirId);

    boolean delFile(int fileId);

    List<FileDir> getDir(int dirId);

    FileDir getFile(int fileId);

    boolean modifyName(FileDir newFileDir);

    boolean move(FileDir oldPosition,FileDir newPosition);

    FileDir getRoot(int userId);

    ResponseEntity<byte[]> downloadFile(String genPath,String fileName);

}

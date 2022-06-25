package com.netdisk.baiguo.service.impl;

import com.netdisk.baiguo.dao.FileDirDao;
import com.netdisk.baiguo.dao.OperationLogDao;
import com.netdisk.baiguo.domain.FileDir;
import com.netdisk.baiguo.domain.OperationLog;
import com.netdisk.baiguo.service.FileDirService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

@Service
public class FileDirServImpl implements FileDirService {

    @Autowired
    private FileDirDao fileDirDao;

    @Value("${file.rootFolder}")
    String rootFolder;

    @Autowired
    private OperationLogDao logDao;

    private long saveFile(String genPath, MultipartFile file) {
        String pic = rootFolder + genPath;
        File dest = new File(pic);
        try {
            file.transferTo(dest);//存储图片
            return dest.length();
        } catch (IOException e) {
            System.out.println("文件保存失败" + pic);
            return 0;
        }
    }

    /*
    private void mkdir(String filePath) throws IOException {
        Path path = Paths.get(rootFolder + filePath);
        Files.createDirectory(path);
    }
*/
    private void reName(String oldPath, String newPath) throws IOException {
        Path abstractOldPath = Paths.get(rootFolder + oldPath);
        Path abstractNewPath = Paths.get(rootFolder + newPath);
        Files.move(abstractOldPath, abstractNewPath);
    }

    private void delFile(List<FileDir> fileDirs) {
        for (FileDir fileDir : fileDirs
        ) {
            if (fileDir.getType() == 0)//如果是目录就递归删除
                delFile(fileDirDao.selectByParent(fileDir.getUser().getId(), 1, fileDir.getId()));
            fileDirDao.updateStatus(fileDir.getId(), 2);
        }
    }

    private FileDir generateFileDir(FileDir fileDir, int type) {
        FileDir parentDir = fileDirDao.selectById(fileDir.getParentId());
        //判断父目录是不是该用户的逻辑抽象到控制层
        String path = parentDir.getPath() + "/" + fileDir.getName();
        fileDir.setPath(path);
        int level = parentDir.getLevel() + 1;
        if (level > 9)
            return null;
        fileDir.setLevel(level);
        fileDir.setType(type);//0文件夹，1文件
        return fileDir;
    }

    @Override
    public boolean addDir(FileDir fileDir) { //要求传入用户id，父目录id，目录名，path重新生成; 用户id在控制层重新检查并设置
        FileDir newFileDir = generateFileDir(fileDir, 0);
        if (newFileDir != null) {
            //0登录，1新建文件夹，2添加文件，3删除文件夹，4删除文件, 5下载
            return  logDao.insertOperationLog(new OperationLog(1, fileDir.getPath(), fileDir.getUser()))==1&&fileDirDao.insertFileDir(fileDir) == 1;
        }
        return false;
    }

    @Override
    public boolean pushFile(FileDir fileDir, MultipartFile file) { //传进来用户id，父目录id，path重新生成，用户id在控制层重新检查并设置
        /*FileDir parentDir = fileDirDao.selectById(fileDir.getParentId());
        //判断父目录是不是该用户的
        if (parentDir == null || parentDir.getUser().getId() != fileDir.getUser().getId())
            return false;
        int level = parentDir.getLevel() + 1;
        if (level > 9)
            return false;
        fileDir.setLevel(level);
        String fileName = file.getOriginalFilename();
        fileDir.setType(1);
        fileDir.setName(fileName);
        fileDir.setPath(parentDir.getPath() + fileName);
        //0登录，1新建文件夹，2添加文件，3删除文件夹，4删除文件, 5下载
        logDao.insertOperationLog(new OperationLog(2,fileDir.getPath(),fileDir.getUser()));
        long size = upLoadFile(fileDir.getPath(),file)/1024/1024;
        fileDir.setSize(size+"M");
        fileDirDao.insertFileDir(fileDir);
        return false;
        */

        fileDir.setName(file.getOriginalFilename());
        FileDir newFileDir = generateFileDir(fileDir, 1);
        if (newFileDir != null) {
            logDao.insertOperationLog(new OperationLog(2, fileDir.getPath(), fileDir.getUser()));
            String uuid = UUID.randomUUID().toString().replace("-", "");
            fileDir.setGenPath(fileDir.getUser().getId() + "/" + uuid + fileDir.getName()); //真实地址是用户文件夹下的"uuid+文件名"的文件
            float size = (float) saveFile(fileDir.getGenPath(), file);
            if (size < 1024)
                fileDir.setSize(size + "B");
            else if (size < 1048576)
                fileDir.setSize(String.format("%.2f",size / 1024) + "KB");
            else if(size<1073741824)
                fileDir.setSize(String.format("%.2f",size/1048576) + "MB");
            else fileDir.setSize(String.format("%.2f", size/1073741824)+"GB");
            return fileDirDao.insertFileDir(fileDir) == 1;
        }
        return false;
    }


    @Override
    public boolean delDir(int dirId) {//目录id
        FileDir currentDir = fileDirDao.selectById(dirId);
        //递归列表，删除文件
        delFile(fileDirDao.selectByParent(currentDir.getUser().getId(), 1, dirId));
        //0登录，1新建文件夹，2添加文件，3删除文件夹，4删除文件, 5下载
        logDao.insertOperationLog(new OperationLog(3, currentDir.getPath(), currentDir.getUser()));
        fileDirDao.updateStatus(dirId, 2);
        return true;
    }


    @Override
    public boolean delFile(int fileId) {//查看目录的时候记得只查看status==1的，用户id在控制层重新检查并设置; 记得把文件（非目录）放到删除表里。
        FileDir currentDir = fileDirDao.selectById(fileId);
        //0登录，1新建文件夹，2添加文件，3删除文件夹，4删除文件, 5下载
        logDao.insertOperationLog(new OperationLog(4, currentDir.getPath(), currentDir.getUser()));
        return fileDirDao.updateStatus( fileId, 2) == 1;
    }

    @Override
    public List<FileDir> getDir(int dirId) {
        FileDir currentDir = fileDirDao.selectById(dirId);
        return fileDirDao.selectByParent(currentDir.getUser().getId(), 1, currentDir.getId());

    }

    @Override
    public FileDir getFile(int fileId) {
        return fileDirDao.selectById(fileId);
    }

    //没有递归重命名，而且改了名字之后，恢复旧的会出bug，所以取消重命名功能
    //要实现的话，要递归修改path
    @Override
    public boolean modifyName(FileDir newFileDir) {//把新名字放在name上，从数据库取旧名字
        /*FileDir currentDir = fileDirDao.selectById(newFileDir.getId());//这里面的名字是旧名字
        //判断文件是不是该用户的
        if (currentDir == null || currentDir.getUser().getId() != newFileDir.getUser().getId())
            return false;
        try {
            fileDirDao.updateName(newFileDir);
            newFileDir = fileDirDao.selectById(newFileDir.getId());
            reName(currentDir.getPath(), newFileDir.getPath());
            //0登录，1新建文件夹，2添加文件，3删除文件夹，4删除文件, 5下载
            logDao.insertOperationLog(new OperationLog(7, currentDir.getPath()+"改为"+newFileDir.getPath(), newFileDir.getUser()));
            return true;
        } catch (IOException e) {
            return false;
        }*/
        return false;
    }

    @Override
    public boolean move(FileDir oldPosition, FileDir newPosition) {
        //暂时不要移动功能
        return false;
    }

    @Override
    public FileDir getRoot(int userId) {
        return fileDirDao.selectRoot(userId);
    }


    @Override
    public ResponseEntity<byte[]> downloadFile(String genPath, String fileName) {
        // 根路径加上传参数的路径构成文件路径地址
        String realPath = rootFolder + genPath;
        File file = new File(realPath);
        // 构建响应
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        bodyBuilder.contentLength(file.length());
        // 二进制数据流
        bodyBuilder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // 文件名编码
        try {
            //String encodeFileName = URLEncoder.encode(filename, StandardCharsets.UTF_8);
            //if (inline) {//要不要在线打开
            // 在浏览器中打开
            //URL url = new URL("file:///" + file);
            //bodyBuilder.header("Content-Type", url.openConnection().getContentType());
            // bodyBuilder.header("Content-Disposition", "inline;filename*=UTF-8''" + encodeFileName);
            // } else {
            // 直接下载
            //bodyBuilder.header("Content-Disposition", "attachment;filename*=UTF-8''" + encodeFileName);
            bodyBuilder.header("Content-Disposition", "attachment;filename*=UTF-8''" + fileName);
            //}

            // 下载成功返回二进制流
            return bodyBuilder.body(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 下载失败直接返回错误的请求
        return null;

    }
}

package com.netdisk.baiguo.controller;

import com.netdisk.baiguo.domain.OperationLog;
import com.netdisk.baiguo.service.LogService;
import com.netdisk.baiguo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @RequestMapping("/getLog")
    public List<OperationLog> getLog(@CookieValue(name = "userId") int userId){
        return logService.getAll(userId);
    }
}

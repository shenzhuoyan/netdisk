package com.netdisk.baiguo.service.impl;

import com.netdisk.baiguo.dao.OperationLogDao;
import com.netdisk.baiguo.domain.OperationLog;
import com.netdisk.baiguo.domain.User;
import com.netdisk.baiguo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServImpl implements LogService {

    @Autowired
    private OperationLogDao operationLogDao;
    @Override
    public List<OperationLog> getAll(int userId) {//要求在控制层判断是否是用户本人

        return operationLogDao.selectOperationLog(userId);
    }
}

package com.netdisk.baiguo.service;

import com.netdisk.baiguo.domain.OperationLog;
import com.netdisk.baiguo.domain.User;

import java.util.List;

public interface LogService {
    List<OperationLog> getAll(int userId);
}

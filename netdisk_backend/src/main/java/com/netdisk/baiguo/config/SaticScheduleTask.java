package com.netdisk.baiguo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration    //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    //3.添加定时任务，每天0点查询已删除且30天前的内容并删除
    @Scheduled(cron = "0 0 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)

    private void configureTasks() {
        System.err.println("这里应该是定时删除, but I don't think write" + LocalDateTime.now());
    }
}
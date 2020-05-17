package com.fy.exception.common.exception.handler;

import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 创建一个线程池，，提交多线程任务保存异常到数据库
 * @author: Yu
 * @create: 2020-05-17 11:51
 **/
@Service
public class ExceptionSave {

    /*
     * 创建一个线程的实例
     * 核心线程大小5
     * 最大线程大小10
     * 线程没有处理任务的时候存活的时间
     * 创建一个数组阻塞队列（队列的长度为5）
     * */
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 8000,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());


    //创建一个线程池，，提交多线程任务保存异常到数据库
    public void saveException(Exception e) {

        Runnable task = () -> {

            //do something dao层保存到数据库
        };

        executor.execute(task);
    }
}

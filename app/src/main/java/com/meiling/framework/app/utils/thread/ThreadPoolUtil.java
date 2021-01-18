package com.meiling.framework.app.utils.thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单线程，线程池管理工具
 */
public class ThreadPoolUtil {
    private ExecutorService singleThreadPool;

    private ThreadPoolUtil() {
        singleThreadPool = Executors.newFixedThreadPool(10);
    }

    public static ThreadPoolUtil getInstance() {
        return ThreadPoolHolder.instances;
    }

    /**
     * 在子线程中自己进行处理，可使用Handler来处理线程切换
     *
     *
     * @param runnable
     */
    public void submit(Runnable runnable) {
       singleThreadPool.submit(runnable);
    }

    public void shotDownNow(){
        singleThreadPool.shutdownNow();
    }

    private static class ThreadPoolHolder {
        private static ThreadPoolUtil instances = new ThreadPoolUtil();
    }
}

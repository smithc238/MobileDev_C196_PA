package com.mySchool.mobiledev_c196_pa.data.repository;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class MySchoolExecutorService {
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private MySchoolExecutorService() {}

    public static ExecutorService getService() {
        return service;
    }

}

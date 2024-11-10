package com.jingdianjichi.subject.application.controller;

public class VolatileExample {
    private volatile boolean flag = false;
    
    public void writer() {
        flag = true;
    }
    
    public void reader() {
        if (flag) {
            // 读取到最新的flag值
            System.out.println("Flag is true");
        }
    }
    
    public static void main(String[] args) {
        VolatileExample example = new VolatileExample();
        
        Thread writerThread = new Thread(() -> {
            example.writer();
        });
        
        Thread readerThread = new Thread(() -> {
            example.reader();
        });
        
        writerThread.start();
        readerThread.start();
    }
}
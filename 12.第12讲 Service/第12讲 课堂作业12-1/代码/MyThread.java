package com.example.liysuzy.threadhomework;

public class MyThread extends Thread {
    @Override
    public void run() {
        while (true){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("new thread is running!");
        }

    }
}


package com.example.manualThread;

public class ManualThread {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new RunnableTask(i));
            thread.start();
        }
    }
}

class RunnableTask implements Runnable {
    private final int taskId;

    public RunnableTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task " + taskId + " is running");
    }
}
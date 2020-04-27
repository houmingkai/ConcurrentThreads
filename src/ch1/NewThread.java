package ch1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *  创建线程的三种方式
 *
 *  一、继承Thread类创建
 *  二、 通过Runnable接口创建线程类
 *  三、 使用Callable和Future创建线程
 */
public class NewThread extends Thread{

    private int i = 0 ;

    @Override
    public void run() {
        for(;i<50;i++){
            System.out.println(Thread.currentThread().getName() + " is running " + i );
        }
    }

    //实现Runnable接口,不允许有返回值
    public static class UserRun implements Runnable{

        @Override
        public void run() {
            System.out.println(" implements Runnable.....");
        }
    }

    //实现Callable接口,允许有返回值,可以抛异常
    public static class UserCall implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println(" implements Callable.....");
            return "Callable";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        NewThread newThread = new NewThread();
//        newThread.start();

        UserRun userRun = new UserRun();
        new Thread(userRun).start();

        UserCall userCall = new UserCall();
        FutureTask<String> futureTask = new FutureTask(userCall);
        new Thread(futureTask).start();
        //获取返回值  futureTask.get()是阻塞的
        System.out.println(futureTask.get());
    }
}

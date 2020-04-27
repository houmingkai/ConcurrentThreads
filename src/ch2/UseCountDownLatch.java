package ch2;

import utils.SleepUtils;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 *   CountDownLatch----加强版的join()
 *
 *
 * 演示CountDownLatch的用法 ,五个初始化线程，6个扣除点（扣除点的数量一定要大于等于初始化线程数量）
 *
 *  所有的扣除点扣除完毕后,主线程和业务线程才能继续自己的工作
 */
public class UseCountDownLatch {

    // 6个扣除点
    static CountDownLatch latch = new CountDownLatch(6);

    //初始化线程
    private static class InitThread implements Runnable{

        @Override
        public void run() {
            System.out.println("Thread_"+Thread.currentThread().getId()+" ready init ........");
            latch.countDown();
            //初始化线程做一些业务操作
            System.out.println("Thread_"+Thread.currentThread().getId()+" do its work ........");
                
        }
    }

    //业务线程
    private static class BusiThread implements Runnable{
        @Override
        public void run() {
            try {
                //业务线程等待
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //业务线程做一些业务操作
            System.out.println("业务线程开始执行 ........");

        }
    }

    public static void main(String[] args) {

        //单独的初始化线程，初始化分为两步,需要扣除两次
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SleepUtils.ms(1);
//                System.out.println("Thread_"+Thread.currentThread().getId()+" ready init 1st ........");
//                latch.countDown();
//
//                SleepUtils.ms(1);
//                System.out.println("Thread_"+Thread.currentThread().getId()+" ready init 2nd ........");
//                latch.countDown();
//            }
//        }).start();
        //lambda实现上面的线程
        new Thread(() -> {
            SleepUtils.ms(1);
            System.out.println("Thread_"+Thread.currentThread().getId()+" ready init 1st ........");
            latch.countDown();

            SleepUtils.ms(1);
            System.out.println("Thread_"+Thread.currentThread().getId()+" ready init 2nd ........");
            latch.countDown();
        }).start();

        //一个业务线程
        new Thread(new BusiThread()).start();
        //四个初始化线程
//        try {
//            //主线程等待
//            latch.await();
//            System.out.println("主线程开始执行..............");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        IntStream.range(0, 4).mapToObj(i -> new Thread(new InitThread())).forEach(Thread::start);


    }
}

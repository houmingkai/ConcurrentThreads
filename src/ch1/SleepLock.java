package ch1;

import java.time.LocalDateTime;


/**
 *
 *  线程调用sleep()方法后,该线程持有的锁不释放
 *
 *  不要在同步代码块(synchronized)里面调用sleep()方法
 *
 * sleep()方法和yield()方法的区别:
 * 线程调用sleep()方法后,该线程进入休眠,退出竞争CPU------肯定不执行
 * 线程调用yield()方法后,不退出竞争CPU---有可能执行
 *
 */
public class SleepLock {

    private Object lock = new Object();

    //休眠线程
    private class ThreadSleep extends Thread{
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName+" will be take the lock");

            synchronized (lock){
                System.out.println(threadName+" taking the lock");
                try {
                    Thread.sleep(5000);
                    System.out.println(threadName+" finish work ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //不休眠线程
    private class ThreadNotSleep extends Thread{
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName+" will be take the lock ................,time= "+ LocalDateTime.now());

            synchronized (lock){
                System.out.println(threadName+" taking the lock,time= "+LocalDateTime.now());
                System.out.println(threadName+" finish work ");
            }
        }
    }

    public static void main(String[] args) {
        SleepLock sleepLock = new SleepLock();
        Thread threadSleep = sleepLock.new ThreadSleep();
        threadSleep.setName("ThreadSleep");

        Thread threadNotSleep = sleepLock.new ThreadNotSleep();
        threadNotSleep.setName("ThreadNotSleep");

        threadSleep.start();

        threadNotSleep.start();

    }
}

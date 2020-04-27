package ch1;

import utils.SleepUtils;

/**
 *  join方法使用实例
 *
 *  线程A, 执行了线程B的join方法,线程A必须要等待线程B执行完成了以后,线程A才能继续自己 的工作
 */
public class UseJoin {

    static class JumpQueue implements  Runnable{
        //用来插队的线程
        private Thread thread;

        public JumpQueue(Thread thread){
            this.thread = thread;
        }
        @Override
        public void run() {
            try {
                System.out.println(thread.getName()+" will be join before "+Thread.currentThread().getName());
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"执行完毕");
        }
    }

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();

        for (int i = 0; i < 3; i++) {
            //i=0时,previous是主线程;i=1时,previous是i=0这个线程
           Thread thread = new Thread(new JumpQueue(previous),String.valueOf(i));
            System.out.println(previous.getName()+" jump a queue the thread :"+thread.getName());
            thread.start();
            previous = thread;
        }

        SleepUtils.second(2);  //主线程休眠2秒
        System.out.println(Thread.currentThread().getName()+" terminate");
    }
}

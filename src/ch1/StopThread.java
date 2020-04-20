package ch1;


/**
 *
 * stop() resume() suspend() 等方法已经过时
 *
 * 安全的中断线程方式:
 *  java线程是协作式，为了让每个线程有时间去做一些清理工作
 *  interrupt()   中断一个线程,并不是强行关闭这个线程，通知这个线程需要终止(是否终止还是看线程本身),中断标志位 置为ture
 *  isInterrupted()   判断当前线程是否处于中断状态
 *  static方法 interrupted()  判断当前线程是否处于中断状态,中断标志位改为false
 */
public class StopThread {

    //Thread stop
    private static  class UseThread extends Thread{
        public UseThread(String name){
            super(name);
        }
        @Override
        public void run(){
            String threadName = Thread.currentThread().getName();
            while (!isInterrupted()){
                System.out.println(threadName + "is run !");
            }

            System.out.println(threadName + " interrupt flag is "+isInterrupted());
        }


        //Runnable stop
        private static  class UseRunnable implements Runnable{

            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                while (!Thread.currentThread().isInterrupted()){
                    System.out.println(threadName + "is run !");
                }

                System.out.println(threadName + " interrupt flag is "+ Thread.currentThread().isInterrupted());
            }
        }


        public static void main(String[] args) throws InterruptedException {
            //Thread stop
//            Thread endThread = new UseThread("endThread");
////            endThread.start();
////            Thread.sleep(10);
////            endThread.interrupt();

            //Runnable stop
            UseRunnable useRunnable = new UseRunnable();
            Thread thread = new Thread(useRunnable,"endRunnable");
            thread.start();
            Thread.sleep(20);
            thread.interrupt();

        }
    }
}

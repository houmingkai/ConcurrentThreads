package ch2;


import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * 一组线程达到某个屏障，被阻塞，一直到组内最后一个线程达到屏障时，
 *
 * CyclicBarrier(int parties)：屏障开放，所有被阻塞的线程会继续运行
 *
 *
 *  CyclicBarrier(int parties, Runnable barrierAction)：屏障开放，barrierAction定义的任务会被执行
 */
public class UseCyclicBarrier {

    private static CyclicBarrier barrier = new CyclicBarrier(5,new CollectThread());
//    private static CyclicBarrier barrier = new CyclicBarrier(5);

    //存放子线程工作结果
    private static ConcurrentHashMap<String,Long> resultMap = new  ConcurrentHashMap();

    //屏障打开后，执行的线程
    private static class CollectThread implements  Runnable{

        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String,Long> workResult : resultMap.entrySet()) {
                sb.append("["+workResult.getValue()+"]");
            }
            System.out.println("the result = "+sb);
        }
    }

    //工作线程
    private static class SubThread implements Runnable{
        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId()+"",id);
            Random r  = new Random();
            try {
                if(r.nextBoolean()){  //r.nextBoolean() 随机生成true  or false
                    Thread.sleep(2000+id);
                    System.out.println("Thread_"+id+"工作线程进入休眠..........");
                }

                System.out.println("Thread_"+id+" is await ........");
                //等待其他工作线程全部就位
                barrier.await();
                Thread.sleep(1000+id);
                System.out.println("Thread_"+id+" is working ........");

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }
}

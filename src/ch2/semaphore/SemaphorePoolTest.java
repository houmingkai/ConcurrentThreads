package ch2.semaphore;

import utils.SleepUtils;

import java.sql.Connection;
import java.util.Random;
import java.util.stream.IntStream;

public class SemaphorePoolTest {

    private static DBPoolSemaphore dbPool = new DBPoolSemaphore();

    private static class BusiThread extends  Thread{
        @Override
        public void run() {
            Random r = new Random();
            long start = System.currentTimeMillis();
            try {
                //获取连接
                Connection connection = dbPool.takeConnect();
                System.out.println("Thread_"+Thread.currentThread().getId()+
                        "获取数据库连接共耗时:"+(System.currentTimeMillis()-start)+"ms");
                SleepUtils.ms(100+r.nextInt(100));
                System.out.println("Thread_"+Thread.currentThread().getId()+"_DB操作完成,归还连接");
                dbPool.returnConnect(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        for (int i = 0; i <50 ; i++) {
//            Thread thread = new BusiThread();
//            thread.start();
//        }
        IntStream.range(0, 50).mapToObj(i -> new BusiThread()).forEach(Thread::start);
    }
}

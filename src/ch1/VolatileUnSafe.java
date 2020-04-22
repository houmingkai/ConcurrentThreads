package ch1;

import utils.SleepUtils;

/**
 *  volatile无法保证操作的原子性
 */
public class VolatileUnSafe {

    private static class VolatileVar implements Runnable{

        private volatile int a = 0;
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            a += 1;
            System.out.println(threadName+":=========== first :"+a);
            SleepUtils.ms(100);
            a += 1;
            System.out.println(threadName+":=========== second :"+a);
        }
    }

    public static void main(String[] args) {
        VolatileVar v = new VolatileVar();

        Thread t1 = new Thread(v);
        Thread t2 = new Thread(v);
        Thread t3 = new Thread(v);
        Thread t4 = new Thread(v);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

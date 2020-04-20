package ch1;

/**
 *
 * sleep() ,join(),wait() 等方法会抛出InterruptedException 异常
 * java中提供的所以逇阻塞方法，都会抛InterruptedException 异常
 *
 * 方法 抛出InterruptedException异常时,会把线程的中断标志位复位成false,造成线程无法中断
 *   若想正常中断,需要在catch 中手动调用 interrupt()
 *
 */
public class HasInterruptedException {

    private static class UseThread extends Thread {
        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (!isInterrupted()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println(threadName + " interrupt flag is " + isInterrupted());
                    interrupt();
                    e.printStackTrace();
                }

                System.out.println(threadName + " 111111");
            }

            System.out.println(threadName + " !!!!!!!!!!!  interrupt flag is " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
            //Thread stop
            Thread endThread = new UseThread("HasInterruptedException");
            endThread.start();
            Thread.sleep(1000);
            endThread.interrupt();
    }
}

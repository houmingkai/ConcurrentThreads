package ch1;


/**
 *  使用ThreadLocal 的多个线程之间不会互相影响
 */
public class UseThreadLocal {

    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    /**
     *  运行三个线程
     */
    public void startThread(){
        Thread[] runs = new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new Thread(new TestThread(i));
            runs[i].start();
        }
    }

    //改变ThreadLocal变量的值,并写回，看线程之间是否会互相影响
    public static class TestThread implements Runnable{
        int id;
        public TestThread(int id) {
            this.id = id;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+": start");
            Integer s = threadLocal.get();  //获取变量的值
            s = s+id;
            threadLocal.set(s);    //设置变量的值
            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.startThread();
    }

}

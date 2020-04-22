package ch1;

import utils.SleepUtils;

/**
 *  对象锁和类锁
 *
 *  普通方法用synchronized修饰时,是对象锁,对象锁的是new出来的对象实例
 *  static方法用synchronized修饰时,是类锁,类锁锁的是这个类的class对象
 */
public class SynClzAndInstance {

    //使用类锁的线程
    private static class SynClass extends  Thread{
        @Override
        public void run() {
            System.out.println("SynClass is Running.......");
            SynClass();
        }
    }

    //使用对象锁的线程
    private static class InstanceSyn implements Runnable{
        private SynClzAndInstance synClzAndInstance;

        public InstanceSyn(SynClzAndInstance synClzAndInstance) {
            this.synClzAndInstance = synClzAndInstance;
        }

        @Override
        public void run() {
            System.out.println("InstanceSyn is Running......."+synClzAndInstance);
            synClzAndInstance.instance();
        }
    }

    //使用对象锁的线程
    private static class InstanceSyn2 implements Runnable{
        private SynClzAndInstance synClzAndInstance;

        public InstanceSyn2(SynClzAndInstance synClzAndInstance) {
            this.synClzAndInstance = synClzAndInstance;
        }
        @Override
        public void run() {
            System.out.println("InstanceSyn is Running......."+synClzAndInstance);
            synClzAndInstance.instance2();
        }
    }

    private synchronized void instance(){

        SleepUtils.second(3);
        System.out.println("sysInstance is Running......."+this.toString());
        SleepUtils.second(3);
        System.out.println("sysInstance ended......."+this.toString());
    }

    private synchronized void instance2(){
        SleepUtils.second(3);
        System.out.println("sysInstance2 is Running......."+this.toString());
        SleepUtils.second(3);
        System.out.println("sysInstance2 ended......."+this.toString());
    }

    private static synchronized void SynClass(){
        SleepUtils.second(1);
        System.out.println("SynClass is Running.......");
        SleepUtils.second(1);
        System.out.println("SynClass ended.......");
    }


    public static void main(String[] args) {
        //对象锁，两个线程锁的是两个对象时候，这两个线程是可以同时运行的
//        SynClzAndInstance instance = new SynClzAndInstance();
//        Thread t1 = new Thread(new InstanceSyn(instance));
//
//        SynClzAndInstance instance2 = new SynClzAndInstance();
//        Thread t2 = new Thread(new InstanceSyn(instance2));

        //对象锁，两个线程锁的是同一个对象时候，这两个线程是有先后顺序的(不能同时进行)
//        SynClzAndInstance instance = new SynClzAndInstance();
//        Thread t1 = new Thread(new InstanceSyn(instance));
//        Thread t2 = new Thread(new InstanceSyn(instance));

//        t1.start();
//        t2.start();

        //类锁和对象锁是可以同时运行的
        // 类锁锁的是这个类的class对象
        SynClzAndInstance instance = new SynClzAndInstance();
        Thread t1 = new Thread(new InstanceSyn(instance));
        t1.start();
        SynClass synClass = new SynClass();
        synClass.start();
        SleepUtils.second(1);
    }

}

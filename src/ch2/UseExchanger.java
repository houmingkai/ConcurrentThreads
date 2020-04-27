package ch2;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 *  Exchanger
 *  exchange(V x) :交换数据，阻塞方法(抛InterruptedException 异常)
 *
 *  两个线程之间交换数据
 */
public class UseExchanger {

    private static final Exchanger<Set<String>> exchanger = new Exchanger();

    public static void main(String[] args) {

        new Thread(() -> {
            Set<String> setA = new HashSet<>();
            try {
                for (int i = 0; i < 5 ; i++) {
                    setA.add("A_"+i);
                }
                setA = exchanger.exchange(setA);
                System.out.println("setA："+setA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(()-> {
            try {
                Set<String> setB = new HashSet<>();
                for (int i = 15; i < 20 ; i++) {
                    setB.add("B_"+i);
                }
                setB  = exchanger.exchange(setB);
                System.out.println("setB："+setB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();





    }


}

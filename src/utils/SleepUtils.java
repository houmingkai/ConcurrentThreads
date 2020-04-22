package utils;

import java.util.concurrent.TimeUnit;

public class SleepUtils {

    //休眠n秒
    public static final void second(int n){
        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //休眠n毫秒
    public static final void ms(int n){
        try {
            TimeUnit.MILLISECONDS.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package ch3;

import java.util.concurrent.atomic.AtomicStampedReference;

public class UseAtomicStampedReference {

    static AtomicStampedReference<String> asr = new AtomicStampedReference("test",0);

    public static void main(String[] args) throws InterruptedException {
        int oldStamp = asr.getStamp();
        String oldReference = asr.getReference();
        System.out.println("Old:============"+oldStamp+"==============="+oldReference);

        Thread right = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "" +
                    "当前变量值:" + oldReference + "当前版本号:" + oldStamp + "=======" +
                    asr.compareAndSet(oldReference, "new_" + oldReference, oldStamp, oldStamp + 1));
        });


        Thread error = new Thread(() -> {
            String reference = asr.getReference();
            System.out.println(Thread.currentThread().getName() + "" +
                    "当前变量值:" + reference + "当前版本号:" + asr.getStamp() + "=======" +
                    asr.compareAndSet(reference, "new2_" + reference, oldStamp, oldStamp + 1));
        });

        right.start();
        right.join();

        error.start();
        error.join();

        System.out.println(asr.getReference()+"============="+asr.getStamp());
    }
}

package ch3;

import java.util.concurrent.atomic.AtomicInteger;

public class UseAtomicInteger {

    static AtomicInteger integer = new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println(integer.getAndIncrement());    //10--->11
        System.out.println(integer.incrementAndGet());   //11--->12
        System.out.println(integer.get());
    }
}

package ch3;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class UseAtomicArray {

    static int[] value = {1,2,3};

    static  AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        ai.getAndSet(2,1111);
        System.out.println(ai);
        System.out.println(Arrays.toString(value));
    }
}

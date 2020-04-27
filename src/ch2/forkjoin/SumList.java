package ch2.forkjoin;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
 * 使用foek-join框架，进行集合中数字累加
 *
 * RecursiveTask<T>  有返回值
 *
 * 使用二分法切割集合
 */
public class SumList {

    public static int length = 4000;


    /**
     *
     * RecursiveTask<T>  有返回值
     *
     * RecursiveAction   无返回值
     */
    private static class SumTask extends RecursiveTask<Integer>{

        public final static int Threshold = length/10;

        private List<Integer> src;
        private int beginIndex;
        private int endIndex;

        public SumTask(List<Integer> src, int beginIndex, int endIndex) {
            this.src = src;
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
        }

        @Override
        protected Integer compute() {
            if(endIndex - beginIndex < Threshold){
                int count = 0;
                for (int i = beginIndex; i < endIndex; i++) {
                    count = count + src.get(i);
                }
                return count;
            }else{
                //使用二分法切割集合
                int mid = (beginIndex+endIndex)/2;
                SumTask left = new SumTask(src,beginIndex,mid);
                SumTask right = new SumTask(src,mid+1,endIndex);
                invokeAll(left,right);
                return left.join()+right.join();
            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        List<Integer> integers = makeList();
        SumTask sumTask = new SumTask(integers,0,integers.size()-1);
        //ForkJoin框架的同步调用
        pool.invoke(sumTask);

        System.out.println("result is "+sumTask.join());
        System.out.println("spend time  is "+(System.currentTimeMillis()-start)+"ms");
    }

    public static List<Integer> makeList(){
        Random r = new Random();
        List<Integer> integers = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            integers.add(r.nextInt(length*3));
        }
        return integers;
    }
}

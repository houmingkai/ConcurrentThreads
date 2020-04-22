package ch1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;

/**
 *   打印main方法的所有线程
 */
public class OnlyMain {
    public static void main(String[] args) {
        //虚拟机管理线程的接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        Arrays.asList(threadInfos).stream().forEach(x ->
                    System.out.println("["+x.getThreadId()+"] :"+x.getThreadName()));

    }
}

package ch2.forkjoin;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 *  遍历制定目录(包含子目录),寻找制定类型的文件
 *
 *  RecursiveAction   无返回值
 */
public class FindDirsFiles extends RecursiveAction {

    private static String findPath = "D:/";
    private static String findName = ".txt";

    private File path;  //当前任务需要搜寻的目录

    public FindDirsFiles(File path) {
        this.path = path;
    }


    public static void main(String[] args) {
        try {
            ForkJoinPool pool = new ForkJoinPool();
            FindDirsFiles task = new FindDirsFiles(new File(findPath));

            //ForkJoin框架的异步调用
            pool.execute(task);

            System.out.println("task is Running ..........");
            Thread.sleep(1);
            task.join();
            System.out.println("task end ..........");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void compute() {
        List<FindDirsFiles> list = new ArrayList<>();
        File[] files = path.listFiles();
        if(files!=null){
//            for (File file : files) {
//                if(file.isDirectory()){
//                    list.add(new FindDirsFiles(file));
//                }else {
//                    if(file.getAbsolutePath().contains(findName)){
//                        System.out.println("文件名称: "+file.getAbsolutePath());
//                    }
//                }
//            }
            Arrays.stream(files).forEach(file -> {
                if (file.isDirectory()) {
                    list.add(new FindDirsFiles(file));
                } else {
                    if (file.getAbsolutePath().contains(findName)) {
                        System.out.println("文件名称: " + file.getAbsolutePath());
                    }
                }
            });

            if(list.size() > 0){
                //invokeAll ---把任务提交给fork-join框架
                Collection<FindDirsFiles> findDirsFiles = invokeAll(list);
//                for (FindDirsFiles findDirsFile : findDirsFiles) {
//                    //等待子任务执行完成
//                    findDirsFile.join();
//                }
                //等待子任务执行完成
                findDirsFiles.forEach(ForkJoinTask::join);
            }
        }
    }
}

package ch2.semaphore;


import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 *   Semaphore 控制同时访问某个资源的线程数量,用在流量控制
 *
 *   方法解析:
 *    Semaphore(int permits) :定义Semaphore中允许同时存在的线程数量(许可证数量)
 *     acquire():获取许可证    ---阻塞方法(抛InterruptedException)
 *     release():归还许可证
 *     availablePermits()：当前Semaphore中还有多少许可证可用
 *     getQueueLength(): 当前等待许可证的线程数量
 *
 *
 *   使用Semaphore 实现一个数据库连接池
 *
 */
public class DBPoolSemaphore {

    //连接池大小
    private final static int POOL_SIZE = 10;

    //useful表示可用的数据库连接,useless表示已经使用的连接
    private final Semaphore useful,useless;

    //定义Semaphore初始数量
    public DBPoolSemaphore(){
        this.useful = new Semaphore(POOL_SIZE);
        this.useless = new Semaphore(0);
    }

    //存放数据库连接的容器
    private static LinkedList<Connection> pool = new LinkedList();
    //初始化池
    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
    }

    //归还连接
    public void returnConnect(Connection connection) throws InterruptedException {
        if(connection != null){
            System.out.println("当前有"+useful.getQueueLength()+"个线程等待数据库连接");
            System.out.println("可用连接数量："+useless.availablePermits());
            useless.acquire();
            synchronized (pool){
                pool.addLast(connection);
            }
            useful.release();
        }

    }

    //获取连接
    public Connection takeConnect() throws InterruptedException {
        //获取许可证
        useful.acquire();

        Connection connection;
        synchronized (pool){
            connection = pool.removeFirst();
        }
        useless.release();
        return connection;
    }

}

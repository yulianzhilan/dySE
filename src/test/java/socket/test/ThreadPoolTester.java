package socket.test;

import socket.ThreadPool;

/**
 * Created by scott on 2017/3/10.
 */
public class ThreadPoolTester {
    public static void main(String[] args) {
//        if(args.length != 2){
//            System.out.println("*************");
//            System.out.println("task num: ");
//            System.out.println("*************");
//        }
//        int numTasks = Integer.parseInt(args[0]);
//        int poolSize = Integer.parseInt(args[1]);
        int numTasks = 6;
        int poolSize = 3;

        ThreadPool threadPool = new ThreadPool(poolSize);

        for(int i=0; i<numTasks; i++){
            threadPool.execute(createTask(i));
        }
        threadPool.join();
    }

    private static Runnable createTask(final int taskId){
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("task"+taskId+":start");
                try{
                    Thread.sleep(500);
                } catch(InterruptedException e){
                    System.out.println("task"+taskId+":end");
                }
            }
        };
    }
}

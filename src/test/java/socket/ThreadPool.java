package socket;

import java.util.LinkedList;

/**
 * Created by scott on 2017/3/10.
 */
public class ThreadPool extends ThreadGroup {
    private boolean isClosed = false;
    private LinkedList<Runnable> workQueue;
    private static int threadPoolId;
    private int threadId;

    public ThreadPool(int poolSize){
        super("socket.ThreadPool-"+(threadPoolId++));
        setDaemon(true);
        workQueue = new LinkedList<>();
        for(int i=0; i<poolSize; i++){
            new WorkThread().start();
        }
    }

    public synchronized void execute(Runnable task){
        if(isClosed){
            throw new IllegalStateException();
        }
        if(task != null){
            workQueue.add(task);
            notify();
        }
    }

    protected synchronized Runnable getTask() throws InterruptedException {
        while(workQueue.size() == 0){
            if(isClosed){
                return null;
            }
            wait();
        }
        return workQueue.removeFirst();
    }

    public synchronized void close(){
        if(!isClosed){
            isClosed = true;
            workQueue.clear();
            interrupt();
        }
    }

    public void join(){
        synchronized (this){
            isClosed = true;
            notifyAll();
        }

        Thread[] threads = new Thread[activeCount()];

        int count = enumerate(threads);
        for(int i=0; i<count; i++){
            try{
                threads[i].join();
            } catch(InterruptedException e){}
        }
    }

    private class WorkThread extends Thread{
        public WorkThread(){
            super(ThreadPool.this, "WorkThread-"+(threadId++));
        }

        public void run(){
            while(!isInterrupted()){
                Runnable task = null;
                try{
                    task = getTask();
                } catch(InterruptedException e){}

                if(task == null){
                    return;
                }

                try{
                    task.run();
                } catch(Throwable t){
                    t.printStackTrace();
                }
            }
        }
    }
}

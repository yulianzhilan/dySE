package spider;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by scott on 2017/3/6.
 */
public class Dispatcher {
    private ArrayList<URL> urls = new ArrayList<>();
    private ArrayList<URL> visitedURLs = new ArrayList<>();
    private ArrayList<URL> unvisitedURLs = new ArrayList<>();

    public Dispatcher(ArrayList<URL> urls){
        this.urls = urls;
    }

    public synchronized URL getURL(){
        while(urls.isEmpty()){
            try{
                wait();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        this.notify();
        URL url = urls.get(0);
        visitedURLs.add(url);
        unvisitedURLs.remove(url);
        return url;
    }

    public synchronized void insert(URL url){
        if(!urls.contains(url) && !visitedURLs.contains(url)){
            urls.add(url);
        }
    }

    public synchronized void insert(ArrayList<URL> analyzedURL){
        for(URL url : analyzedURL){
            if(!urls.contains(url) && !visitedURLs.contains(url)){
                urls.add(url);
            }
        }
    }
}

package spider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by scott on 2017/3/7.
 */
public class Spider {
    private ArrayList<URL> urls;
    private int gatherNum = 5;

    public Spider() {}

    public Spider(ArrayList<URL> urls) {
        this.urls = urls;
    }

    public void start(){
        Dispatcher dispatcher = new Dispatcher(urls);
        for(int i=0; i<gatherNum; i++){
            Thread gather = new Thread(new Gather(dispatcher, String.valueOf(i)));
            gather.start();
        }
    }

    public static void main(String[] args){
        ArrayList<URL> urls = new ArrayList<>();
        try{
            urls.add(new URL("http://www.163.com"));
        } catch(MalformedURLException e){
            e.printStackTrace();
        }
        Spider spider = new Spider(urls);
        spider.start();
    }
}

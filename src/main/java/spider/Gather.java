package spider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by scott on 2017/3/6.
 */
public class Gather implements Runnable{
    private Dispatcher dispatcher;
    private String ID;
    private URLClient client = new URLClient();
    private WebAnalyzer analyzer = new WebAnalyzer();
    private File file;
    private BufferedWriter bfWriter;

    public Gather(Dispatcher dispatcher, String ID) {
        this.dispatcher = dispatcher;
        this.ID = ID;

        file = new File("Raws\\RAW_"+ID+".txt");
        try{
            file.createNewFile();
            bfWriter = new BufferedWriter(new FileWriter(file));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int counter = 0;
        while(counter++ <= 2){
            URL url = dispatcher.getURL();
            System.out.println("in running: "+ID+" get url: "+url.toString());
            String htmlDoc = client.getDocumentAt(url);

            if(htmlDoc.length() != 0){
                ArrayList<URL> newURL = analyzer.doAnalyzer(bfWriter, url, htmlDoc);
                if(newURL.size() != 0){
                    dispatcher.insert(newURL);
                }
            }
        }
    }
}

package spider;

import util.HtmlParser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by scott on 2017/3/7.
 */
public class WebAnalyzer {
//    private static final String ENDPAGE = "****************************";

    public WebAnalyzer() {}

    public ArrayList<URL> doAnalyzer(BufferedWriter bfWriter, URL url, String htmlDoc){
        System.out.println("in doing analyzer the size of doc is "+htmlDoc.length());
        ArrayList<URL> urlInHtmlDoc = (new HtmlParser()).urlDetector(htmlDoc);
        saveDoc(bfWriter, url, htmlDoc);
        return urlInHtmlDoc;
    }

    private void saveDoc(BufferedWriter bfWriter, URL url, String htmlDoc){
        try{
            String versionStr = "version:1.0\n";
            String URLStr = "url:"+url.toString()+"\n";

            Date date = new Date();
            String dateStr = "date:"+date.toString()+"\n";

            InetAddress address = InetAddress.getByName(url.getHost());
            String IPStr =address.toString();
            IPStr = "IP: "+IPStr.substring(IPStr.indexOf("/")+1, IPStr.length())+"\n";

            String htmlLen = "length: "+htmlDoc.length()+"\n";

            bfWriter.append(versionStr);
            bfWriter.append(URLStr);
            bfWriter.append(dateStr);
            bfWriter.append(IPStr);
            bfWriter.append(htmlLen);
            bfWriter.newLine();

            bfWriter.append(htmlDoc);
            bfWriter.newLine();
            bfWriter.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

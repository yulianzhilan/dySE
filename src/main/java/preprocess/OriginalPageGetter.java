package preprocess;

import configure.Configuration;
import util.DBConnection;
import util.MD5;
import util.Page;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by scott on 2017/3/6.
 */
public class OriginalPageGetter {
    private String url = "";
    private DBConnection dbc = new DBConnection();
    private MD5 md5 = new MD5();
    private String date = "";
    private String urlFromHead = "";
    private Configuration conf = new Configuration();

    public OriginalPageGetter() {}

    public OriginalPageGetter(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public String getPage(){
        Page page = getRawsInfo(url);
        String content = "";
        try{
            StringBuffer tfileName = new StringBuffer();
            tfileName.append(page.getRawName());
            tfileName.insert(4, "\\");
            String fileName = conf.getValue("RAWSPATH" + "\\" + tfileName.toString());
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bfReader = new BufferedReader(fileReader);

            String word;
            bfReader.skip(page.getOffset());

            readRawHead(bfReader);
            content = readRawContent(bfReader);
            String contentMD5 = md5.getMD5ofStr(content);

            if(contentMD5.equals(page.getContent())){
                System.out.println("same");
            } else{
                System.out.println("different");
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return content;
    }

    // 重载的getPage函数，改叫getContent，通过文件名和偏移量得到网页内容
    public String getContent(String file, int offset){
        String content = "";
        BufferedReader bfReader = null;
        try{
            StringBuffer tfileName = new StringBuffer();
            tfileName.append(file);
            tfileName.insert(4,"\\");
            String fileName = conf.getValue("RAWSPATH")+"\\"+tfileName.toString();

            FileReader fileReader = new FileReader(fileName.toString());
            bfReader = new BufferedReader(fileReader);

            String word;
            bfReader.skip(offset);
            readRawHead(bfReader);
            content = readRawContent(bfReader);

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            if(bfReader != null){
                try{
                    bfReader.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    public Page getRawsInfo(String url){
        String sql = "select * from pageindex where url = '"+url+"'";
        ResultSet rs = dbc.executeQuery(sql);

        String content = "";
        String raws = "";
        int offset = 0;
        try{
            while(rs.next()){
                content = rs.getString("content");
                raws = rs.getString("raws");
                offset = Integer.parseInt(rs.getString("offset"));
                System.out.println(url + "\t" + content + "\t" + offset + "\t" + raws);
            }
            return new Page(url, offset, content, raws);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private String readRawHead(BufferedReader bfReader){
        String headStr = "";
        try{
            bfReader.readLine();
            urlFromHead = bfReader.readLine();
            headStr += urlFromHead;
            if(urlFromHead != null) {
                urlFromHead = urlFromHead.substring(urlFromHead.indexOf(":" + 1), urlFromHead.length());
            }
            date = bfReader.readLine();
            headStr += date;
            if(date != null){
                date = date.substring(date.indexOf(":")+1, date.length());
            }
            String temp;
            while(!(temp = bfReader.readLine()).trim().isEmpty()){
                headStr += temp;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return headStr;
    }

    private String readRawContent(BufferedReader bfReader){
        StringBuffer strBuffer = new StringBuffer();
        try{
            String word;
            while((word = bfReader.readLine()) != null){
                if(word.trim().isEmpty()){
                    break;
                } else{
                    strBuffer.append(word + "\n");
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return strBuffer.toString();
    }
}


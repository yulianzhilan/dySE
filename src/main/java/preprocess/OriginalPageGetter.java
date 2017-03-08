package preprocess;

import configure.Configuration;
import sun.security.provider.MD5;
import util.DBConnection;
import util.Page;

import java.io.BufferedReader;
import java.io.FileReader;
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
//        Page page = getRawsInfo(url);
//        String content = "";
//        try{
//
//        }
    }

    public String getContent(String file, int offset){
        String content = "";
        BufferedReader bufferedReader = null;
        try{
            StringBuffer tfileName = new StringBuffer();
            tfileName.append(file);
            tfileName.insert(4,"\\");
            String fileName = conf.getValue("RAWSPATH")+"\\"+tfileName.toString();

            FileReader fileReader = new FileReader(fileName.toString());
            bufferedReader = new BufferedReader(fileReader);

            String word;
            bufferedReader.skip(offset);


        }
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
                offset = Integer.parseInt(rs.getString("raws"));
                System.out.println(url + "\t");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}


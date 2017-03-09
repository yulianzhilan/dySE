package preprocess;

import util.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by scott on 2017/3/9.
 */
public class ForwardIndex {
    private DBConnection dbc = new DBConnection();
    private HashMap<String, ArrayList<String>> indexMap = new HashMap<>();
    private OriginalPageGetter pageGetter = new OriginalPageGetter();
    private DictSegment dictSeg = new DictSegment();

    public ForwardIndex() {}

    public HashMap<String, ArrayList<String>> creatForwordIndex(){
        try{
            ArrayList<String> segResult = new ArrayList<>();
            String sql = "select * from pageindex";
            ResultSet rs = dbc.executeQuery(sql);
            String url, fileName;
            int offset;

            System.out.println("in the process of creating forwardIndex: ");
            while(rs.next()){
                url = rs.getString("url");
                System.out.println(url);

                if(url.equals("http://www.sogou.com/")) {
                    System.out.println();
                }
                fileName = rs.getString("raws");
                offset = Integer.parseInt(rs.getString("offset"));
                String htmlDoc = pageGetter.getContent(fileName, offset);

                segResult = dictSeg.SegmentFile(htmlDoc);
                indexMap.put(url, segResult);
            }
            rs.close();
        } catch(SQLException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("create forwardIndex finished!!");
        System.out.println("the size of forwardIndex is : " + indexMap.size());

        return indexMap;
    }
}

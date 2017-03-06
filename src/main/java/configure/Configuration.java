package configure;

import java.io.*;
import java.util.Properties;

/**
 * Created by scott on 2017/3/6.
 */
public class Configuration {
    /**
     * 读取properties文件
     */
    private Properties propertie;
    private FileInputStream in;
    private FileOutputStream out;

    public Configuration(){
        propertie = new Properties();
        try{
            File file = new File("configure.properties");
            in = new FileInputStream(file);
            propertie.load(in);
            out = new FileOutputStream(file,true);
//            propertie.load(getClass().getClassLoader().getResourceAsStream("configure.properties"));
        } catch(FileNotFoundException e){
            System.out.println("文件路径错误或者文件不存在");
            e.printStackTrace();
        } catch(IOException e){
            System.out.println("装载文件失败");
            e.printStackTrace();
        }
    }

    public String getValue(String key){
        if(propertie.containsKey(key)){
            String value = propertie.getProperty(key);
            return value;
        } else
            return "";
    }

    public static void main(String [] args){
        Configuration conf = new Configuration();
        String rawsPath = conf.getValue("RAWSPATH");
        String dictPath = conf.getValue("DICTPATH");
        String mysqlPath = conf.getValue("MYSQLLIBPATH");

        System.out.println("the rawsPath is: " + rawsPath);
        System.out.println("the dictPath is: " + dictPath);
        System.out.println("the mysqlPath is: " + mysqlPath);
    }

}

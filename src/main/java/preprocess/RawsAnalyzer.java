package preprocess;

import util.DBConnection;
import util.MD5;
import util.Page;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by scott on 2017/3/9.
 */
public class RawsAnalyzer {
    private DBConnection dbc = new DBConnection();
    private MD5 md5 = new MD5();
    private int offset;
    private Page page;
    private String rootDictionary;

    public RawsAnalyzer(String rootDictionary) {
        this.rootDictionary = rootDictionary;
        page = new Page();
    }

    public void createPageIndex(){
        ArrayList<String> fileNames = getSubFile(rootDictionary);
        for(String fileName : fileNames){
            createPageIndex(fileName);
        }
    }

    public void createPageIndex(String fileName){
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bfReader = new BufferedReader(fileReader);

            String word;
            offset = 0;
            int oldOffset;

            while((word = bfReader.readLine()) != null){
                oldOffset = offset;
                oldOffset += word.length() + 1;
                String url = readRawHead(bfReader);
                String content = readRawContent(bfReader);

                System.out.println("the offset in " + fileName + " is " + offset);
                String contentMD5 = md5.getMD5ofStr(content);
                page.setPage(url, oldOffset, contentMD5, fileName);
                page.add2DB(dbc);
            }

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private String readRawHead(BufferedReader bfReader){
        String urlLine = null;
        try{
            urlLine = bfReader.readLine();
            offset = offset + urlLine.length() + 1;
            if(urlLine != null) {
                urlLine = urlLine.substring(urlLine.indexOf(":") + 1, urlLine.length());
            }
            String temp;
            while(!(temp = bfReader.readLine()).trim().isEmpty()){
                offset = offset + temp.length() + 1;
            }
            offset += 1;
        } catch(IOException e){
            e.printStackTrace();
        }
        return urlLine;
    }

    private String readRawContent(BufferedReader bfReader){
        StringBuffer strBuffer = new StringBuffer();
        try{
            String word;
            while((word = bfReader.readLine()) != null){
                offset = offset+word.length()+1;
                if(word.trim().isEmpty()) break;
                else strBuffer.append(word+"\n");
            }
            offset += 2;
        } catch(IOException e){
            e.printStackTrace();
        }
        return strBuffer.toString();
    }

    public static ArrayList<String> getSubFile(String fileName){
        ArrayList<String> fileNames = new ArrayList<>();
        File parentF = new File(fileName);
        if(!parentF.exists()){
            System.out.println("no existing file or direction");
            return null;
        }
        if(parentF.isFile()){
            System.out.println("it is a file");
            fileNames.add(parentF.getAbsolutePath());
            return fileNames;
        }
        System.out.println(fileName + " isn't a file");
        String [] subFiles = parentF.list();
        for(int i=0; i<subFiles.length; i++){
            fileNames.add(fileName+"\\"+subFiles[i]);
        }
        return fileNames;
    }
}

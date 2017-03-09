package preprocess;

import configure.Configuration;
import util.HtmlParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by scott on 2017/3/6.
 */
public class DictSegment {
    private HashSet<String> dict; // dictionary
    private HashSet<String> stopWordDict; // stop dictionary
    private DictReader dictReader = new DictReader();
    private static final int maxLength = 4;
    private static String dictFile = "";
    private static String stopDictFile = "";
    private Configuration conf;

    public DictSegment(){
        conf = new Configuration();
        dictFile = conf.getValue("DICTIONARYPATH");
        stopDictFile = conf.getValue("STOPDICTIONARYPATH");

        dict = dictReader.scanDict(dictFile);
        stopWordDict = dictReader.scanDict(stopDictFile);
    }

    public ArrayList<String> SegmentFile(String htmlDoc){
        // 第一步操作，把html文件用正则表达式处理，去掉标签等
        HtmlParser parser = new HtmlParser();
        String htmlText = parser.html2Text(htmlDoc);

        // 断句、断字
        ArrayList<String> sentances = cutIntoSentance(htmlText);
        ArrayList<String> segResult = new ArrayList<>();
        for(int i =0; i<sentances.size(); i++){
            segResult.addAll(cutIntoWord(sentances.get(i)));
        }
        return segResult;
    }

    protected ArrayList<String> cutIntoSentance(String htmlDoc){

        ArrayList<String> sentance = new ArrayList<>();
        String token = "。，、；：？！“”‘’《》（）-——";
        StringTokenizer tokenizer = new StringTokenizer(htmlDoc,token);
//        int num = tokenizer.countTokens();
        while(tokenizer.hasMoreTokens()){
            sentance.add(tokenizer.nextToken());
        }
        return sentance;
    }

    public ArrayList<String> cutIntoWord(String sentance){
        int currLen;
        String wait2cut = sentance;
        ArrayList<String> sentanceSegResult = new ArrayList<>();
        while(wait2cut.length() != 0){
            String temp;
            if(wait2cut.length() >= maxLength){
                currLen = maxLength;
            } else{
                currLen = wait2cut.length();
            }
            temp = wait2cut.substring(0, currLen);
            while(!dict.contains(temp) && currLen > 1){
                currLen--;
                temp = temp.substring(0, currLen);
            }

            // 判断temp是否在停用词表内
            if(!stopWordDict.contains(temp) && temp.length() != 1){
                sentanceSegResult.add(temp);
            }

            // 句子去除temp长度的字符串
            wait2cut = wait2cut.substring(currLen);
        }
        // fixme 这里输出的是空[]
        System.out.println(sentanceSegResult);
        return sentanceSegResult;
    }

    public static void main(String[] args) {
        DictSegment dictSegment = new DictSegment();
    }
}

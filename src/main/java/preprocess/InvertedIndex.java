package preprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by scott on 2017/3/9.
 */
public class InvertedIndex {
    private HashMap<String, ArrayList<String>> forwardIndexMap;
    private HashMap<String, ArrayList<String>> invertedIndexMap;

    public InvertedIndex() {
        ForwardIndex forwardIndex = new ForwardIndex();
        forwardIndexMap = forwardIndex.creatForwordIndex();
    }

    public HashMap<String, ArrayList<String>> createInvertedIndex(){
        invertedIndexMap = new HashMap<>();
        for(Iterator it = forwardIndexMap.entrySet().iterator(); it.hasNext();){
            Map.Entry entry = (Map.Entry)it.next();
            String url = (String) entry.getKey();
            ArrayList<String> words = (ArrayList<String>) entry.getValue();
            String word;
            for(int i=0; i<words.size(); i++){
                word = words.get(i);
                if(!invertedIndexMap.containsKey(word)){
                    ArrayList<String> urls = new ArrayList<>();
                    urls.add(url);
                    invertedIndexMap.put(word, urls);
                } else{
                    ArrayList<String> urls = invertedIndexMap.get(word);
                    if(!urls.contains(url)){
                        urls.add(url);
                    }
                }
            }
        }

        System.out.println("***************************************************************");
        System.out.println("create invertedIndex finished!!");
        System.out.println("the size of invertedIndex is : " + invertedIndexMap.size());
        return invertedIndexMap;
    }

    public HashMap<String, ArrayList<String>> getInvertedIndex(){
        return invertedIndexMap;
    }
}

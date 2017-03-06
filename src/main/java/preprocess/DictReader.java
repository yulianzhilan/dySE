package preprocess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by scott on 2017/3/6.
 */
public class DictReader {
    public final String dictFile = "hello.txt";

    public DictReader(){}

    public HashSet<String> scanDict(String dictFile){
        HashSet<String> dictionary = new HashSet<>();
        try{
            FileReader fileReader = new FileReader(dictFile);
            BufferedReader bfReader = new BufferedReader(fileReader);
            String word;
            while((word = bfReader.readLine()) != null){
                dictionary.add(word);
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("the size of dictionary is: " + dictionary.size());
//        Iterator iter = dictionary.iterator();
//        int count = 20;
//        while (iter.hasNext() && count -- >= 0){
//            System.out.println(iter.next());
//            System.out.println(count);
//        }
        return dictionary;
    }
}

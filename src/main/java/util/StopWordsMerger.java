package util;

import java.util.HashSet;

/**
 * Created by scott on 2017/3/9.
 */
public class StopWordsMerger {
    public StopWordsMerger() {
    }

    public HashSet<String> mergeSet(HashSet<String> set1, HashSet<String> set2){
        HashSet<String> wordSet = new HashSet<>();

        wordSet.addAll(set1);
        wordSet.addAll(set2);

        System.out.println(wordSet.size());
        return wordSet;
    }
}

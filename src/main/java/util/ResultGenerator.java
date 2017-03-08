package util;

import preprocess.OriginalPageGetter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by scott on 2017/3/8.
 */
public class ResultGenerator {
    private OriginalPageGetter pageGetter;
    private HtmlParser parser;
    Pattern p_title, p_meta;
    Matcher m_title, m_meta;

    public ResultGenerator() {
        pageGetter = new OriginalPageGetter();
        parser = new HtmlParser();

        String regEx_title = "<title[^>]*?[\\s\\S]*?</title>";
        String regEx_meta = "<meta[\\s\\S]*?>";

        p_title = Pattern.compile(regEx_title, Pattern.CASE_INSENSITIVE);
        p_meta = Pattern.compile(regEx_meta, Pattern.CASE_INSENSITIVE);
    }

    public Result generateResult(String url){
        Page page;
        String content = "";
        String date = "";
        String title = "";
        String shortContent = "";

//        page = pageGetter
    }
}

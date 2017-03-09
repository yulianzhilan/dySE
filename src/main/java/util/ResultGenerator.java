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

        page = pageGetter.getRawsInfo(url);
        content = pageGetter.getContent(page.getRawName(), page.getOffset());

        date = pageGetter.getDate();

        m_title = p_title.matcher(content);
        while(m_title.find()){
            title = m_title.group();
            title = title.substring(title.indexOf(">"+1), title.lastIndexOf("<"));
            break;
        }

        m_meta = p_meta.matcher(content);
        while(m_meta.find()){
            shortContent = m_meta.group();
            shortContent = shortContent.toLowerCase();


            if(shortContent.contains("description")){
                int start = shortContent.indexOf("content=")+9;

                shortContent = (String)shortContent.subSequence(start, shortContent.indexOf("\""));
                int end = shortContent.indexOf("\"");
                shortContent = (String)shortContent.subSequence(0, end);
                break;
            }
        }

        return new Result(title, shortContent, url, date);
    }

    public static void main(String[] args) {

        String target1 = "<meta name=\"Description\" content=\"新浪网为全球用户24小。\" /> ";
        String target2 = "<META content=\"关注搜索门户的最新动态、把握垂直搜索的发展趋势。\" name=description>";

        String regEx = "<meta[\\s\\S]*?>";
        Pattern p_title = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m_title = p_title.matcher(target1);

        String description = "description";

        //获得META的数据
        while (m_title.find()) {

            String desp = m_title.group();
            System.out.println(desp);

            desp = desp.toLowerCase();
            if (desp.contains(description))
                System.out.println("包含description");

            //找到content以及两个""
            int start = desp.indexOf("content=") + 9;
            System.out.println(start);
            System.out.println(desp.charAt(start));

            desp = (String) desp.subSequence(start, desp.length());
            int end = desp.indexOf("\"");
            System.out.println("the end of \" is " + end);
            desp = (String) desp.subSequence(0, end);
            System.out.println("最后的结果");
            System.out.println(desp);

        }
    }
}

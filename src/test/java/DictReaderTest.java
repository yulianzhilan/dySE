import preprocess.DictReader;

/**
 * Created by scott on 2017/3/6.
 */
public class DictReaderTest {
    public static void main(String[] args) {
        DictReader reader = new DictReader();
        reader.scanDict(reader.dictFile);
    }
}

package core.basesyntax.read.file;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderFromFileImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/inputData.csv";
    private static final String INVALID_PATH = " ";
    private static ReaderFromFile readerFromFile;
    private static List<String> dataList;

    @BeforeClass
    public static void setUp() {
        readerFromFile = new ReaderFromFileImpl();
        dataList = new ArrayList<>();
        dataList.add("b,banana,20");
        dataList.add("b,apple,100");
        dataList.add("s,banana,100");
        dataList.add("p,banana,13");
        dataList.add("r,apple,10");
        dataList.add("p,apple,20");
        dataList.add("p,banana,5");
        dataList.add("s,banana,50");
    }

    @Test
    public void readFromFile_readingFile_Ok() {
        List<String[]> fromReader = readerFromFile.readFromFile(INPUT_FILE_PATH);
        List<String> actual = convertList(fromReader);
        List<String> expected = dataList;
        Assert.assertEquals("Actual list doesn't equal expected list.", actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_readingFile_not0k() {
        readerFromFile.readFromFile(INVALID_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullValuePath_not0k() {
        readerFromFile.readFromFile(null);
    }

    private List<String> convertList(List<String[]> data) {
        List<String> stringList = new ArrayList<>();
        for (String[] line : data) {
            stringList.add(String.join(",", line));
        }
        return stringList;
    }
}

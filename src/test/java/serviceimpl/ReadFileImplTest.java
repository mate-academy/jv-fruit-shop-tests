package serviceimpl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileImplTest {
    private static final String VALID_WAY = "src/main/resources/StartDay.csv";
    private static final String INVALID_WAY = "src/main/resources/EmptyFile.csv";
    private static ReadFileImpl readFile;
    private static List<String> expectedFile;

    @BeforeClass
    public static void beforeClass() {
        readFile = new ReadFileImpl();
        expectedFile = new ArrayList<>();
        expectedFile.add("type,fruit,quantity");
        expectedFile.add("b,banana,20");
        expectedFile.add("b,apple,100");
        expectedFile.add("s,banana,100");
        expectedFile.add("p,banana,13");
        expectedFile.add("r,apple,10");
        expectedFile.add("p,apple,20");
        expectedFile.add("p,banana,5");
        expectedFile.add("s,banana,50");
    }

    @Test
    public void readFromFile_OK() {
        List<String> expected = expectedFile;
        List<String> actual = readFile.readFromFile(VALID_WAY);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void invalidWay_Not_Ok() {
        List<String> expected = expectedFile;
        List<String> actual = readFile.readFromFile(INVALID_WAY);
        Assert.assertNotEquals(expected, actual);
    }
}

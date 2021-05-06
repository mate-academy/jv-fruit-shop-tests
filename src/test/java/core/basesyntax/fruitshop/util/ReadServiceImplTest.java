package core.basesyntax.fruitshop.util;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReadServiceImplTest {
    private static List<String> fruitDataLines;
    private static ReadService readService;
    private static String filePath;

    @Before
    public void setUp() {
        readService = new ReadServiceImpl();
        fruitDataLines = new ArrayList<>();
    }

    @Test
    public void readFromFile_BasicInputFile_isOk() {
        fruitDataLines.add("type,fruit,quantity");
        fruitDataLines.add("b,banana,20");
        fruitDataLines.add("b,apple,100");
        fruitDataLines.add("s,banana,100");
        fruitDataLines.add("p,banana,13");
        fruitDataLines.add("r,apple,10");
        filePath = "src/test/resources/test_input_basic.csv";
        List<String> actual = readService.readFromFile(filePath);
        Assert.assertEquals(fruitDataLines, actual);
    }

    @Test
    public void readFromFile_IncorrectRecordsInputFile_isOk() {
        fruitDataLines.add("type,fruit,quantity");
        fruitDataLines.add("bbanana,20.....");
        fruitDataLines.add("b,apple");
        filePath = "src/test/resources/test_input_incorrect_records.csv";
        List<String> actual = readService.readFromFile(filePath);
        Assert.assertEquals(fruitDataLines, actual);
    }

    @Test
    public void readFromFile_EmptyInputFile_isOk() {
        filePath = "src/test/resources/test_input_empty.csv";
        List<String> actual = readService.readFromFile(filePath);
        Assert.assertEquals(fruitDataLines, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_WrongFilePathException_isThrown() {
        filePath = "src/test/resources/test_input_absent.csv";
        readService.readFromFile(filePath);
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_NullFilePathException_isThrown() {
        readService.readFromFile(null);
    }

    @After
    public void tearDown() {
        fruitDataLines.clear();
    }
}

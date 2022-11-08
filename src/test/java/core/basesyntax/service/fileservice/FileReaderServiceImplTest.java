package core.basesyntax.service.fileservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReader;
    private static String validFilePath;
    private static String invalidFilePath;
    private static List<String> expectedList;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderServiceImpl();
        validFilePath = "src/test/resources/testInput.csv";
        invalidFilePath = "notExistingPath";
    }

    @Test
    public void readFile_isOk() {
        expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        assertEquals(expectedList.toString(), fileReader.readFile(validFilePath).toString());
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidFilePath_NotOk() {
        fileReader.readFile(invalidFilePath);
    }
}

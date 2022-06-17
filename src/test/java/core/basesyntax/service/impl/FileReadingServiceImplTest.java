package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReadingService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadingServiceImplTest {
    private static final String FILE_NAME = "src/test/resources/dailyTransactions.csv";
    private static final String FILE_NOT_EXIST_NAME = "src/test/resources/notExist.csv";
    private static FileReadingService fileReader;
    private static List<String> EXPECTED_DATA;

    @BeforeClass
    public static void setFileHandler() {
        fileReader = new FileReadingServiceImpl();
        EXPECTED_DATA = new ArrayList<>();
        EXPECTED_DATA.add(" type,fruit,quantity");
        EXPECTED_DATA.add("    b,banana,20");
        EXPECTED_DATA.add("    b,apple,100");
        EXPECTED_DATA.add("    s,banana,200");
        EXPECTED_DATA.add("    p,banana,13");
    }

    @Test
    public void readFile_ok() {
        List<String> actualData = fileReader.readFile(FILE_NAME);
        assertEquals("Expected: " + EXPECTED_DATA.toString() + "\n"
                + "Actual: " + actualData.toString(), EXPECTED_DATA, actualData);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_fileNotExist_notOk() {
        List<String> actualData = fileReader.readFile(FILE_NOT_EXIST_NAME);
    }
}

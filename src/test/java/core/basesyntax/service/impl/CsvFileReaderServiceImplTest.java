package core.basesyntax.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String EMPTY_FILE_NAME = "";
    private static final String MISSING_FILE_NAME = "src/test/resources/incorrect.csv";
    private static final String CORRECT_FILE_NAME = "src/test/resources/testReadFile.csv";

    private static CsvFileReaderServiceImpl readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new CsvFileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFrom_nullFileName_notOk() {
        readerService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_emptyFileName_notOk() {
        readerService.readFromFile(EMPTY_FILE_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_missingFileName_notOk() {
        readerService.readFromFile(MISSING_FILE_NAME);
    }

    @Test
    public void readFromFile_successRead_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        List<String> actual = readerService.readFromFile(CORRECT_FILE_NAME);
        Assert.assertEquals("There are differences differences between expected and actual result",
                expected, actual);
    }
}

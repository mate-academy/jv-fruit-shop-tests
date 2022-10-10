package core.basesyntax.fileservice.impl;

import core.basesyntax.fileservice.CsvFileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String CORRECT_PATH = "src/test/resources/readTest.csv";
    private static final String INCORRECT_PATH = "src/test/resources/test";
    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();
    private final List<String> expectedList = List
            .of("type,fruit,quantity", "b,banana,20", "b,apple,100");

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongPathToFile_NotOk() {
        csvFileReaderService.readFromFile(INCORRECT_PATH);
    }

    @Test
    public void readFromFile_Ok() {
        List<String> actual = csvFileReaderService.readFromFile(CORRECT_PATH);
        Assert.assertEquals(expectedList, actual);
    }
}

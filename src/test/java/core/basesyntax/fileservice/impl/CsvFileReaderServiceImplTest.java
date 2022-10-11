package core.basesyntax.fileservice.impl;

import core.basesyntax.fileservice.CsvFileReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private final CsvFileReaderService csvFileReaderService = new CsvFileReaderServiceImpl();
    private final List<String> expectedList = List
            .of("type,fruit,quantity", "b,banana,20", "b,apple,100");

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongPathToFile_NotOk() {
        String incorrectPath = "src/test/resources/test";
        csvFileReaderService.readFromFile(incorrectPath);
    }

    @Test
    public void readFromFile_Ok() {
        String correctPath = "src/test/resources/readTest.csv";
        List<String> actual = csvFileReaderService.readFromFile(correctPath);
        Assert.assertEquals(expectedList, actual);
    }
}

package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String VALID_FILE = "src/main/resources/before.csv";
    private static final String EMPTY_FILE = "src/main/resources/emptyFile.csv";
    private static final String INCORRECT_PATH = "src/main/incorrect-path";
    private static CsvFileReaderServiceImpl csvFileReaderService;

    @BeforeClass
    public static void beforeClass() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                                        "b,banana,20",
                                        "b,apple,100",
                                        "s,banana,100",
                                        "p,banana,13",
                                        "r,apple,10",
                                        "p,apple,20",
                                        "p,banana,5",
                                        "s,banana,50");
        List<String> actual = csvFileReaderService.readFromFile(VALID_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromEmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = csvFileReaderService.readFromFile(EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_isNotOk() {
        csvFileReaderService.readFromFile(INCORRECT_PATH);
    }
}

package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceTest {
    private static final String FIRST_INPUT_PATH = "src/test/resources/inputTest.csv";
    private static final String SECOND_INPUT_PATH = "src/test/resources/input2.csv";
    private static final String EMPTY_INPUT_PATH = "src/test/resources/emptyInput.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/input3.csv";
    private static CsvFileReaderService csvFileReaderService;

    @BeforeClass
    public static void beforeClass() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFromFile_readFromFirstInput_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = csvFileReaderService.readFromFile(FIRST_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_readFromSecondInput_Ok() {
        List<String> expected = List.of("t,tomato,50",
                "d,banana,78",
                "48 jeans");
        List<String> actual = csvFileReaderService.readFromFile(SECOND_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_readFromEmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = csvFileReaderService.readFromFile(EMPTY_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_readFromNullPathFile_NotOk() {
        csvFileReaderService.readFromFile(null);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_readFromNonExistingPathFife_NotOk() {
        csvFileReaderService.readFromFile(NON_EXISTING_FILE_PATH);
    }
}

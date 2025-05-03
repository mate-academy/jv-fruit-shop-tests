package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String PATH_EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String PATH_VALID_DATA_FILE = "src/test/resources/validDataFile.csv";
    private static final String PATH_NON_EXISTENT_FILE = "src/test/resources/notExist.csv";
    private static final List<String> DEFAULT_INPUT = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static FileReaderService csvFileReader;

    @BeforeClass
    public static void beforeClass() {
        csvFileReader = new CsvFileReaderServiceImpl();
    }

    @Test
    public void readFile_emptyFile_Ok() {
        List<String> actual = csvFileReader.readFile(PATH_EMPTY_FILE);
        List<String> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    public void readFile_validData_Ok() {
        List<String> actual = csvFileReader.readFile(PATH_VALID_DATA_FILE);
        assertEquals(DEFAULT_INPUT, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_nonExistentFile_NotOk() {
        csvFileReader.readFile(PATH_NON_EXISTENT_FILE);
    }
}

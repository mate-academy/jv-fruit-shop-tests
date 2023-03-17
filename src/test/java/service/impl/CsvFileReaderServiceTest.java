package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReaderDataService;

public class CsvFileReaderServiceTest {
    private static final String PATH_TO_VALID_FILE = "src/main/resources/InputData.csv";
    private static final String INVALID_PATH_FILE = "src/main/resources/InvalidFileName.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/main/resources/InvalidFileName.csv";
    private static ReaderDataService readerDataService;
    private static List<String> CORRECT_DATA_FROM_FILE;

    @BeforeClass
    public static void beforeClass() {
        readerDataService = new CsvFileReaderService();
        CORRECT_DATA_FROM_FILE = new ArrayList<>();
        CORRECT_DATA_FROM_FILE.add("type,fruit,quantity");
        CORRECT_DATA_FROM_FILE.add("b,banana,20");
        CORRECT_DATA_FROM_FILE.add("b,apple,100");
        CORRECT_DATA_FROM_FILE.add("s,banana,100");
        CORRECT_DATA_FROM_FILE.add("p,banana,13");
        CORRECT_DATA_FROM_FILE.add("r,apple,10");
        CORRECT_DATA_FROM_FILE.add("p,apple,20");
        CORRECT_DATA_FROM_FILE.add("p,banana,5");
        CORRECT_DATA_FROM_FILE.add("s,banana,50");
    }

    @Test
    public void read_validFile_Ok() {
        List<String> actual = readerDataService.read(PATH_TO_VALID_FILE);
        assertEquals(CORRECT_DATA_FROM_FILE, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyFile_NotOk() {
        readerDataService.read(PATH_TO_EMPTY_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonexistentFile_NotOk() {
        readerDataService.read(INVALID_PATH_FILE);
    }
}

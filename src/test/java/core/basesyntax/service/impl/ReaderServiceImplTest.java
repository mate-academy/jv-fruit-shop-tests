package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static String EMPTY_FILE;
    private static String HEADER_ONLY_FILE;
    private static String VALID_FILE;
    private static String NON_EXISTING_FILE;
    private static String NOT_CSV_FILE;

    @BeforeClass
    public static void beforeAll() {
        EMPTY_FILE = "src/test/resources/emptyFile.csv";
        HEADER_ONLY_FILE = "src/test/resources/inputHeaderOnly.csv";
        VALID_FILE = "src/test/resources/inputValid.csv";
        NON_EXISTING_FILE = "src/test/resources/nonExistingFile.csv";
        NOT_CSV_FILE = "src/test/resources/notCsvFile.txt";
    }

    @Test
    public void readFromCsvFile_HeaderOnly_Ok() {
        ReaderService readerService = new ReaderServiceImpl();
        List<String> dataEmptyFile = readerService.readFromCsvFile(HEADER_ONLY_FILE);
        assertEquals(new ArrayList<>(), dataEmptyFile);
        assertEquals(0, dataEmptyFile.size());
    }

    @Test
    public void readFromCsvFile_emptyFile_Ok() {
        ReaderService readerService = new ReaderServiceImpl();
        List<String> dataEmptyFile = readerService.readFromCsvFile(EMPTY_FILE);
        assertEquals(new ArrayList<>(), dataEmptyFile);
        assertEquals(0, dataEmptyFile.size());
    }

    @Test
    public void readFromCsvFile_AllOperationsFile_Ok() {
        ReaderService readerService = new ReaderServiceImpl();
        List<String> dataFromFile = readerService.readFromCsvFile(VALID_FILE);
        List<String> expectedList = new ArrayList<>();
        String firstLine = "b,banana,20";
        String secondLine = "b,apple,100";
        expectedList.add(firstLine);
        expectedList.add(secondLine);

        assertEquals(expectedList, dataFromFile);
        assertEquals(2, dataFromFile.size());
    }

    @Test(expected = RuntimeException.class)
    public void readFromCsvFile_nonExistingFile_NotOk() {
        ReaderService readerService = new ReaderServiceImpl();
        List<String> dataFromFile = readerService.readFromCsvFile(NON_EXISTING_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFromCsvFile_notCsvFile_NotOk() {
        ReaderService readerService = new ReaderServiceImpl();
        List<String> dataFromFile = readerService.readFromCsvFile(NOT_CSV_FILE);
    }
}

package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private static final String VALID_CSV_FILE_PATH = "src/test/resources/valid_input_file.csv";
    private static final String INVALID_CSV_FILE_PATH = "src/test/resources/invalid_input_file.csv";
    private static final String TXT_FILE_PATH = "src/test/resources/basic_test_file.txt";
    private static List<String> expectedList;
    private static CsvFileReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvFileReaderServiceImpl();
        expectedList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        expectedList.clear();
    }

    @Test
    void readFromCsvFile_nullFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromCsvFile(null));
    }

    @Test
    void readFromCsvFile_wrongFileExtension_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromCsvFile(TXT_FILE_PATH));
    }

    @Test
    void readFromCsvFile_nonExistingFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromCsvFile(INVALID_CSV_FILE_PATH));
    }

    @Test
    void readFromCsvFile_validFile_ok() {
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        List<String> actualList = readerService.readFromCsvFile(VALID_CSV_FILE_PATH);
        assertEquals(expectedList, actualList);
    }
}

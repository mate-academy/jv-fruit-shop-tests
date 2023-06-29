package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String VALID_FIlE_PATH = "src/test/java/resources/testValidInput.csv";
    private static final String INVALID_FILE_PATH = "src/test/java/resources/notExistingFile.csv";

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvFileReaderServiceImpl();
    }

    @Test
    void readDataFromTheFileValidFilePath_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100");
        assertIterableEquals(expected,
                readerService.readDataFromTheFile(VALID_FIlE_PATH));
    }

    @Test
    void readDataFromTheFileInvalidFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readDataFromTheFile(INVALID_FILE_PATH));
    }
}
   




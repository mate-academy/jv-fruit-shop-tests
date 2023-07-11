package core.basesyntax.resources;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String VALID_FILE_PATH = "src/test/java/core/basesyntax"
            + "/resources/correctFile.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/invalidFile.csv";

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readDataFromTheFileValidFilePath_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100");
        assertIterableEquals(expected, readerService.readFile(VALID_FILE_PATH));
    }

    @Test
    void readDataFromTheFileInvalidFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFile(INVALID_FILE_PATH));
    }
}

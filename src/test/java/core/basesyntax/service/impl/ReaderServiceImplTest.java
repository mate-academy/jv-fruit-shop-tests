package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.TransactionException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String FIRST_LINE = "b,banana,20";
    private static final String EMPTY_CSV = "src/test/resources/empty.csv";
    private static final String INCORRECT_FILE_PATH_FORMAT = "src/test/resources/input.mp3";
    private static final String INPUT_CSV_WITH_CONTENT = "src/test/resources/input.csv";
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void reader_nullFilePath_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> readerService.readFromFile(null));
    }

    @Test
    void reader_validFilePath_ok() {
        Assertions.assertNotNull(readerService.readFromFile(INPUT_CSV_WITH_CONTENT));
    }

    @Test
    void reader_emptyCsvFile_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> readerService.readFromFile(EMPTY_CSV));
    }

    @Test
    void reader_invalidFileFormat_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> readerService.readFromFile(INCORRECT_FILE_PATH_FORMAT));
    }

    @Test
    void reader_getValidData_ok() {
        List<String> linesFromFile = readerService.readFromFile(INPUT_CSV_WITH_CONTENT);
        String expected = FIRST_LINE;
        String actual = linesFromFile.get(1);
        Assertions.assertEquals(expected, actual);
    }
}

package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String VALID_FILE_PATH = "src/test/resources/test_transaction.csv";
    private static final String NON_VALID_FILE_PATH = "src\\file.txt";
    private static final String EMPTY_FILE_PATH = "src/test/resources/test_transaction_empty.csv";

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_validFile_ok() {
        List<String> strings = readerService.readFromFile(VALID_FILE_PATH);
        int expectedSize = 1;
        assertEquals(expectedSize, strings.size()," list should contains only one transaction");
        String expected = strings.get(0);
        assertEquals(expected, "b,banana,30", "first element of list must be: 'b,banana,30'");
    }

    @Test
    void readFromFile_null_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(null),
                "read from file should throw exception for null fileName");
        String expected = "file Path can't be null";
        assertEquals(expected, runtimeException.getMessage());
    }

    @Test
    void readFromFile_nonValidPath_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(NON_VALID_FILE_PATH));
        String expected = "Can't read from file " + NON_VALID_FILE_PATH;
        assertEquals(expected, runtimeException.getMessage());
    }

    @Test
    void readFromFile_emptyFile_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(EMPTY_FILE_PATH),
                "empty file should throw FruitTransactionException");
        String expected = "File does not contain transactions";
        assertEquals(expected, exception.getMessage());
    }
}

package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitTransactionException;
import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private static FileReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_ok() {
        String validFilePath = "src/test/resources/test_transaction.csv";
        List<String> strings = readerService.readFromFile(validFilePath);
        assertEquals(1, strings.size()," list should contains only one transaction");
        String expected = strings.get(0);
        assertEquals(expected, "b,banana,30",
                "first element of list must be: 'b,banana,30'");
    }

    @Test
    void readFromFile_null_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(null),
                "read from file should throw exception for null fileName");
        String expected = "file Path can't be null";
        assertEquals(expected,runtimeException.getMessage());
    }

    @Test
    void readFromFile_nonValidPath_notOk() {
        String nonValidFilePath = "src\\file.txt";
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(nonValidFilePath));
        String expected = "Can't read from file " + nonValidFilePath;
        assertEquals(expected,runtimeException.getMessage());
    }

    @Test
    void readFromFile_emptyFile_notOk() {
        String emptyFilePath = "src/test/resources/test_transaction_empty.csv";
        FruitTransactionException exception = assertThrows(FruitTransactionException.class,
                () -> readerService.readFromFile(emptyFilePath),
                "empty file should throw FruitTransactionException");
        String expected = "File does not contain transactions";
        assertEquals(expected,exception.getMessage());
    }
}

package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String FILE_DOES_NOT_EXIST_PATH = "src/test/resources/notExist.csv";
    private static final String FILE_TRANSACTIONS_PATH = "src/test/resources/transactions.csv";
    private static ReaderService readerService;

    @BeforeAll
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFile_fileDoesNotExist_notOk() {
        assertThrows(Exception.class,
                () -> readerService.readFile(FILE_DOES_NOT_EXIST_PATH));
    }

    @Test
    void readFIle_existingFile_ok() {
        List<String> linesExpected = List.of("b,apple,100", "b,banana,50");
        List<String> linesActual = readerService.readFile(FILE_TRANSACTIONS_PATH);
        assertIterableEquals(linesExpected, linesActual);
    }

    @Test
    void readFile_nullPath_notOk() {
        assertThrows(Exception.class,
                () -> readerService.readFile(null));
    }
}

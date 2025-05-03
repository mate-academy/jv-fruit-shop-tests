package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String EXIST_FILE = "newFile.CSV";
    private static final String NON_EXIST_FILE = "file1.CSV";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void write_toNull_isNotOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> writerService.writeToFile(null));
        String expected = "File cannot be null";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void write_toNonExistFile_isOk() {
        assertDoesNotThrow(() -> writerService.writeToFile(NON_EXIST_FILE));
    }

    @Test
    void write_toExistFile_isOk() {
        FruitTransactionDao fruitTransactionDao
                = new FruitTransactionDaoImpl();
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "apple", 100);

        FruitTransaction fruitTransaction1
                = FruitTransaction.of(Operation.BALANCE, "banana", 25);

        fruitTransactionDao.addToStorage(fruitTransaction);

        fruitTransactionDao.addToStorage(fruitTransaction1);

        writerService.writeToFile(EXIST_FILE);

        List<String> expected = List.of("fruit, quantity", "apple,100", "banana,25");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(EXIST_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + EXIST_FILE);
        }
        assertIterableEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitTransactions.clear();
    }
}

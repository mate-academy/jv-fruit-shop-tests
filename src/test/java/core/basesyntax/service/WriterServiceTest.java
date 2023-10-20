package core.basesyntax.service;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriterServiceTest {
    private WriterService writerService;
    private static final String EXIST_FILE = "newFile.CSV";
    private static final String NON_EXIST_FILE = "file1.CSV";
    private FruitTransactionDao fruitTransactionDao;

    @BeforeEach
    void beforeEach() {
        writerService = new WriterServiceImpl();
        fruitTransactionDao = new FruitTransactionDaoImpl();
    }

    @Test
    void writeToNull_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null));
    }

    @Test
    void writeToNonExistFile_isOk() {
        assertDoesNotThrow(() -> writerService.writeToFile(NON_EXIST_FILE));
    }

    @Test
    void writeToExistFile_isOk() {
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
            throw new RuntimeException(e);
        }
        assertIterableEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitTransactions.clear();
    }
}
package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReaderServiceTest {
    private static final String EXIST_FILE = "file.CSV";
    private static final String NON_EXIST_FILE = "file1.CSV";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private ReaderService readerService;

    @BeforeEach
    void beforeEach() {
        readerService = new ReaderServiceImpl(new ParserServiceImpl());
    }

    @Test
    void readFromNull_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(null));
    }

    @Test
    void readFromExistFile_isOk() {

        List<FruitTransaction> actual = readerService.readFromFile(EXIST_FILE);

        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, APPLE, 100);

        FruitTransaction fruitTransaction1
                = FruitTransaction.of(Operation.BALANCE, BANANA, 20);

        FruitTransaction fruitTransaction2
                = FruitTransaction.of(Operation.SUPPLY, APPLE, 20);

        FruitTransaction fruitTransaction3
                = FruitTransaction.of(Operation.PURCHASE, APPLE, 50);

        FruitTransaction fruitTransaction4
                = FruitTransaction.of(Operation.RETURN, BANANA, 40);

        FruitTransaction fruitTransaction5
                = FruitTransaction.of(Operation.PURCHASE, BANANA, 25);

        List<FruitTransaction> expected
                = List.of(fruitTransaction, fruitTransaction1, fruitTransaction2
                , fruitTransaction3, fruitTransaction4, fruitTransaction5);
        assertIterableEquals(expected, actual);
    }

    @Test
    void readFromNonExistFile_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(NON_EXIST_FILE));
    }
}
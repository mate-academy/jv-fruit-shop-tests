package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String EXIST_FILE = "file.CSV";
    private static final String NON_EXIST_FILE = "file4.CSV";
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl(new ParserServiceImpl());
    }

    @Test
    void read_fromNull_isNotOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> readerService.readFromFile(null));
        String expected = "File cannot be null";
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void read_fromExistFile_isOk() {
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
                = List.of(fruitTransaction, fruitTransaction1, fruitTransaction2,
                fruitTransaction3, fruitTransaction4, fruitTransaction5);
        assertIterableEquals(expected, actual);
    }

    @Test
    void read_fromNonExistFile_isNotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(NON_EXIST_FILE));
        String actual = exception.getMessage();
        String expected = "Can't read";
        assertTrue(actual.contains(expected));
    }
}

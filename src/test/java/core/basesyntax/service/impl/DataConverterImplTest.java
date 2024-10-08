package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataConverterImplTest {
    private static final List<String> CORRECT_DATA_FROM_FILE = List.of(
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );
    private static final List<String> INCORRECT_DATA = List.of("c,banana,20");

    private static final List<FruitTransaction> CORRECT_FRUIT_TRANSACTION_LIST = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_correctTransaction_Ok() {
        List<FruitTransaction> actual = dataConverter.convertToTransaction(CORRECT_DATA_FROM_FILE);
        List<FruitTransaction> expected = CORRECT_FRUIT_TRANSACTION_LIST;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_unknownOperation_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(INCORRECT_DATA),
                "Expected IllegalArgumentException was not thrown in "
                        + FruitTransaction.Operation.class);
    }
}
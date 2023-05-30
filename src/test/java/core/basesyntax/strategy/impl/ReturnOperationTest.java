package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String FRUIT_NAME = "banana";
    private static final int BALANCE_FRUIT_QUANTITY = 105;
    private static final int FRUIT_QUANTITY = 85;
    private static final int NEGATIVE_FRUIT_QUANTITY = -20;
    private static OperationsStrategy returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        Storage.storage.put(FRUIT_NAME, BALANCE_FRUIT_QUANTITY);
    }

    @Test
    void handle_returnOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT_NAME, FRUIT_QUANTITY);
        returnOperation.handle(fruitTransaction);
        Integer expected = BALANCE_FRUIT_QUANTITY + FRUIT_QUANTITY;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT_NAME, NEGATIVE_FRUIT_QUANTITY);
        Assertions.assertThrows(RuntimeException.class, () ->
                returnOperation.handle(fruitTransaction));
    }
}

package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String BANANA = "banana";
    private static OperationsStrategy returnOperation;

    @BeforeAll
    static void beforeAll() {
        returnOperation = new ReturnOperation();
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    void handle_returnOperation_ok() {
        Storage.storage.put(BANANA, 105);
        FruitTransaction fruitTransaction = new FruitTransaction(
                RETURN, BANANA, 85);
        returnOperation.handle(fruitTransaction);
        Integer expected = 190;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                RETURN, BANANA, -20);
        Assertions.assertThrows(RuntimeException.class, () ->
                returnOperation.handle(fruitTransaction));
    }
}

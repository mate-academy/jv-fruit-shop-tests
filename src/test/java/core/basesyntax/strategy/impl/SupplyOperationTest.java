package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String BANANA = "banana";
    private static OperationsStrategy supplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    void handle_supplyOperation_ok() {
        Storage.storage.put(BANANA, 105);
        FruitTransaction fruitTransaction = new FruitTransaction(
                SUPPLY, BANANA, 85);
        supplyOperation.handle(fruitTransaction);
        Integer expected = 190;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_invalidQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                SUPPLY, BANANA, -20);
        Assertions.assertThrows(RuntimeException.class, () ->
                supplyOperation.handle(fruitTransaction));
    }
}

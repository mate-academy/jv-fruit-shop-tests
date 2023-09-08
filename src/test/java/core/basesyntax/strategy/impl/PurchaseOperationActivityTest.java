package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationActivity;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationActivityTest {
    private static OperationActivity operationActivity;

    @BeforeAll
    static void beforeAll() {
        operationActivity = new PurchaseOperationActivity();
    }

    @BeforeEach
    void setUp() {
        operationActivity = new PurchaseOperationActivity();
        Storage.fruitTransactionsMap.clear();
    }

    @Test
    void purchaseOperation_notEnoughCountFruits_notOk() {
        Storage.fruitTransactionsMap.put("banana", 5);
        assertThrows(RuntimeException.class, () -> operationActivity.handleTransaction(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10)));
    }

    @Test
    void purchaseOperation_validData_Ok() {
        Map<String, Integer> expected = Map.of("banana", 10);
        Storage.fruitTransactionsMap.put("banana", 20);
        operationActivity.handleTransaction(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));
        Map<String,Integer> actual = Storage.fruitTransactionsMap;
        assertEquals(expected, actual);
    }
}

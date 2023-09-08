package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationActivity;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationActivityTest {
    private static OperationActivity operationActivity;

    @BeforeAll
    static void beforeAll() {
        operationActivity = new ReturnOperationActivity();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitTransactionsMap.clear();
    }

    @Test
    void supplyOperation_fruitExist_Ok() {
        Map<String, Integer> expected = Map.of("apple", 20);
        Storage.fruitTransactionsMap.put("apple", 10);
        operationActivity.handleTransaction(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10));
        Map<String, Integer> actual = Storage.fruitTransactionsMap;
        assertEquals(expected, actual);
    }

    @Test
    void supplyOperation_emptyStorage_Ok() {
        Map<String, Integer> expected = Map.of("apple", 20);
        operationActivity.handleTransaction(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 20));
        Map<String, Integer> actual = Storage.fruitTransactionsMap;
        assertEquals(expected, actual);
    }
}

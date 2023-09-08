package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationActivity;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationActivityTest {

    private static OperationActivity operationActivity;

    @BeforeAll
    static void beforeAll() {
        operationActivity = new ReturnOperationActivity();
    }

    @BeforeEach
    void setUp() {
        operationActivity = new ReturnOperationActivity();
        Storage.fruitTransactionsMap.clear();
    }

    @Test
    void returnOperation_fruitExist_Ok() {
        Map<String, Integer> expected = Map.of("apple", 10);
        Storage.fruitTransactionsMap.put("apple", 4);
        operationActivity.handleTransaction(
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "apple", 6));
        Map<String, Integer> actual = Storage.fruitTransactionsMap;
        assertEquals(expected, actual);
    }

    @Test
    void returnedOperation_emptyStorage_Ok() {
        Map<String, Integer> expected = Map.of("apple", 10);
        operationActivity.handleTransaction(
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "apple", 10));
        Map<String, Integer> actual = Storage.fruitTransactionsMap;
        assertEquals(expected, actual);
    }
}

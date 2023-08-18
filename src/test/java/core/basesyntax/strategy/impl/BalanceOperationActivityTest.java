package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationActivity;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationActivityTest {
    private static OperationActivity operationActivity;

    @BeforeAll
    static void beforeAll() {
        operationActivity = new BalanceOperationActivity();
        Storage.fruitTransactionsMap.clear();
    }

    @Test
    void balanceOperation_validData_Ok() {
        Map<String, Integer> expected = Map.of("banana", 5);
        operationActivity.handleTransaction(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 5));
        Map<String, Integer> actual = Storage.fruitTransactionsMap;
        assertEquals(expected, actual);
    }
}

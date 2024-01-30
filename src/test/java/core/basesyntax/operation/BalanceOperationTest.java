package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final OperationHandler balanceOperation = new BalanceOperation();

    @Test
    void processOperation_correctData_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        balanceOperation.processOperation(fruitTransaction);
        Map<String, Integer> expected = Map.of(
                fruitTransaction.getFruit(), fruitTransaction.getAmount());
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}

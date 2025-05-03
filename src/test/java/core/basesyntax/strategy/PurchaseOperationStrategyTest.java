package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.Operation;
import core.basesyntax.storage.FruitStorage;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationStrategyTest {
    private static final OperationHandler operationHandler = new PurchaseOperationStrategy();

    @Before
    public void cleanStorage() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void handleTest_OK() {
        FruitTransaction fruitTransaction1
                = new FruitTransaction(Operation.PURCHASE,
                "banana", 100);

        FruitTransaction fruitTransaction2
                = new FruitTransaction(Operation.PURCHASE,
                "banana", 20);

        Map<String, Integer> expected1 = Map.of("banana", -100);
        operationHandler.handle(fruitTransaction1);
        Map<String, Integer> actual = FruitStorage.fruitStorage;
        assertEquals(expected1, actual);

        Map<String, Integer> expected2 = Map.of("banana", -120);
        operationHandler.handle(fruitTransaction2);
        assertEquals(expected2, actual);
    }

    @Test
    public void handleTest_NegativeBalance_notOK() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.PURCHASE,
                "banana", -20);

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction);
        });
    }
}

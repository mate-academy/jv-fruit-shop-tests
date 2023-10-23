package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.Operation;
import core.basesyntax.storage.FruitStorage;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationStrategyTest {
    private static final OperationHandler operationHandler = new ReturnOperationStrategy();

    @Before
    public void cleanStorage() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void handleTest_OK() {
        final FruitTransaction fruitTransaction1
                = new FruitTransaction(Operation.RETURN,
                "banana", 100);

        final FruitTransaction fruitTransaction2
                = new FruitTransaction(Operation.RETURN,
                "banana", 20);

        final FruitTransaction fruitTransaction3
                = new FruitTransaction(Operation.RETURN,
                "banana", -20);

        Map<String, Integer> expected1 = Map.of("banana", 100);
        operationHandler.handle(fruitTransaction1);
        Map<String, Integer> actual = FruitStorage.fruitStorage;
        assertEquals(expected1, actual);

        Map<String, Integer> expected2 = Map.of("banana", 120);
        operationHandler.handle(fruitTransaction2);
        assertEquals(expected2, actual);

        Map<String, Integer> expected3 = Map.of("banana", 100);
        operationHandler.handle(fruitTransaction3);
        assertEquals(expected3, actual);
    }
}

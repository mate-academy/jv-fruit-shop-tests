package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static OperationStrategy operationStrategy;
    private static final Map<FruitTransaction.Operation, OperationHandler>
            operationHandlersMap = new HashMap<>();
    private final FruitTransaction.Operation operation = FruitTransaction.Operation.RETURN;

    @BeforeAll
    static void beforeAll() {
        operationHandlersMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void apply_ReturnReturnOperation_ToNotExist_Ok() {
        String fruit = "orange";
        FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, 10);
        List<FruitTransaction> transactions = List.of(fruitTransaction);

        OperationHandler actualHandler = operationStrategy.get(transactions.get(0).getOperation());
        actualHandler.apply(transactions.get(0));

        int expectedAmount = 10;
        assertEquals(expectedAmount, Storage.fruits.get(fruit));
    }

    @Test
    void apply_ReturnReturnOperation_ToExistingFruit_Ok() {
        String fruit = "orange";
        Storage.fruits.put(fruit, 10);

        FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, 10);
        List<FruitTransaction> transactions = List.of(fruitTransaction);

        OperationHandler actualHandler = operationStrategy.get(transactions.get(0).getOperation());
        actualHandler.apply(transactions.get(0));

        int expectedAmount = 20;
        assertEquals(expectedAmount, Storage.fruits.get(fruit));
    }
}

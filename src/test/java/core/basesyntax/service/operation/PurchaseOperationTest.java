package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

class PurchaseOperationTest {
    private static OperationStrategy operationStrategy;
    private static final Map<FruitTransaction.Operation, OperationHandler>
            operationHandlersMap = new HashMap<>();
    private final FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;

    @BeforeAll
    static void beforeAll() {
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void apply_ReturnPurchaseOperation_ToNotExist_NotOk() {
        String fruit = "banana";
        FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, 10);
        List<FruitTransaction> transactions = List.of(fruitTransaction);

        OperationHandler actualHandler = operationStrategy.get(transactions.get(0).getOperation());
        assertThrows(RuntimeException.class, () -> actualHandler.apply(transactions.get(0)));
    }

    @Test
    void apply_ReturnPurchaseOperation_ToExistingFruit_Ok() {
        String fruit = "banana";
        Storage.fruits.put(fruit, 20);

        FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, 10);
        List<FruitTransaction> transactions = List.of(fruitTransaction);

        OperationHandler actualHandler = operationStrategy.get(transactions.get(0).getOperation());
        actualHandler.apply(transactions.get(0));

        int expectedAmount = 10;
        assertEquals(expectedAmount, Storage.fruits.get(fruit));
    }

    @Test
    void apply_ReturnPurchaseOperation_ToExistingFruit_NotOk() {
        String fruit = "banana";
        Storage.fruits.put(fruit, 20);

        FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, 30);
        List<FruitTransaction> transactions = List.of(fruitTransaction);

        OperationHandler actualHandler = operationStrategy.get(transactions.get(0).getOperation());

        assertThrows(RuntimeException.class, () -> actualHandler.apply(transactions.get(0)));

    }
}

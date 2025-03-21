package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static OperationStrategy operationStrategy;
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void operation_StrategyReturnCorrectHandler_Ok() {
        Storage.fruits.clear();
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        String fruit = "apple";
        FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, 50);
        List<FruitTransaction> transactions = List.of(fruitTransaction);
        shopService.process(transactions);
        int expectedResult = 50;

        assertEquals(expectedResult,Storage.fruits.get(fruit));
    }
}

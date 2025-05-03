package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.implementation.FruitTransactionServiceImpl;
import core.basesyntax.service.implementation.OperationStrategyServiceImpl;
import core.basesyntax.service.implementation.ProcessFruitTransactionServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.implementation.BalanceOperationStrategy;
import core.basesyntax.strategy.implementation.PurchaseOperationStrategy;
import core.basesyntax.strategy.implementation.ReturnOperationStrategy;
import core.basesyntax.strategy.implementation.SupplyOperationStrategy;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class ProcessFruitTransactionServiceImplTest {
    private static final Map<FruitTransaction.Operation, OperationStrategy> operationStrategyMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationStrategy(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationStrategy(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationStrategy(),
            FruitTransaction.Operation.RETURN, new ReturnOperationStrategy());
    private static final List<String> INPUT_LINES = List.of(
            "b,banana,20",
            "b,apple,100",
            "s,banana,100");
    private static final String BANANA_KEY = "banana";
    private static final String APPLE_KEY = "apple";
    private static final int BANANA_VALUE = 120;
    private static final int APPLE_VALUE = 100;

    private OperationStrategyService operationStrategyService =
            new OperationStrategyServiceImpl(operationStrategyMap);
    private FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl();
    private ProcessFruitTransactionService processFruitTransactionService =
            new ProcessFruitTransactionServiceImpl(
                    fruitTransactionService, operationStrategyService);

    @After
    public void clear() {
        FruitStorage.clear();
    }

    @Test
    public void processFruitTransaction_Ok() {
        processFruitTransactionService.processFruitTransaction(INPUT_LINES);
        assertResultForKey(BANANA_KEY, BANANA_VALUE);
        assertResultForKey(APPLE_KEY, APPLE_VALUE);
    }

    private void assertResultForKey(String key, Integer value) {
        assertTrue(FruitStorage.contains(key));
        assertEquals(value, FruitStorage.get(key));
    }
}

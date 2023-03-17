package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.implementation.OperationStrategyServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.implementation.BalanceOperationStrategy;
import core.basesyntax.strategy.implementation.PurchaseOperationStrategy;
import core.basesyntax.strategy.implementation.ReturnOperationStrategy;
import core.basesyntax.strategy.implementation.SupplyOperationStrategy;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class OperationStrategyServiceImplTest {
    private static final Map<FruitTransaction.Operation, OperationStrategy> operationStrategyMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationStrategy(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationStrategy(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationStrategy(),
            FruitTransaction.Operation.RETURN, new ReturnOperationStrategy());
    private static final String KEY = "banana";
    private static final String BANANA_FRUIT = "banana";
    private static final int BANANA_QUANTITY = 120;
    private static final int EXPECTED_VALUE = 120;

    private OperationStrategyService operationStrategyService =
            new OperationStrategyServiceImpl(operationStrategyMap);
    private FruitTransaction fruitTransaction;

    @After
    public void clear() {
        FruitStorage.clear();
    }

    @Test
    public void applyOperationStrategy_Ok() {
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA_FRUIT, BANANA_QUANTITY);
        operationStrategyService.applyOperationStrategy(fruitTransaction);
        assertTrue(FruitStorage.contains(KEY));
        assertEquals(EXPECTED_VALUE, (int) FruitStorage.get(KEY));
    }

    @Test (expected = RuntimeException.class)
        public void applyOperationStrategy_nullOperation_notOk() {
        fruitTransaction = new FruitTransaction(
                null, BANANA_FRUIT, BANANA_QUANTITY);
        operationStrategyService.applyOperationStrategy(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for null operation, but it wasn't");
    }
}

package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerStrategyTest {
    private OperationHandlerStrategy operationHandlerStrategy;

    @Before
    public void setUp() {
        Storage.fruits.clear();
        Map<FruitTransaction.Operation, OperationHandler> operationStrategies = new HashMap<>();
        operationStrategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationStrategies.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategies.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationStrategies.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerStrategy = new OperationHandlerStrategy(operationStrategies);
    }

    @Test
    public void get_rightAction_Ok() {
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 43);
        OperationHandler handler = operationHandlerStrategy.get(item);
        handler.handle(item);
        int quantity = Storage.fruits.get("banana");
        Assert.assertEquals(quantity, 43);
    }
}

package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> strategyOperations;

    @Before
    public void setUp() {
        strategyOperations = new HashMap<>();
        strategyOperations.put(BALANCE, new BalanceHandlerOperation());
        strategyOperations.put(SUPPLY, new SupplyHandlerOperation());
        strategyOperations.put(PURCHASE, new PurchaseHandlerOperation());
        strategyOperations.put(RETURN, new ReturnHandlerOperation());
        operationStrategy = new OperationStrategyImpl(strategyOperations);
    }

    @Test
    public void get_CorrectOperation_Ok() {
        OperationHandler actualHandler = operationStrategy.get(BALANCE);
        assertEquals(BalanceHandlerOperation.class, actualHandler.getClass());
    }

    @Test
    public void get_InvalidOperationReturnNull_NotOk() {
        operationStrategy.get(null);
    }
}

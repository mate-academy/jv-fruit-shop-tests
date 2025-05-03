package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        Storage.fruits.put("apple", 10);
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 7);
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getOperationHandler_validData_Ok() {
        OperationHandler operationHandler = operationStrategy
                .getOperationHandler(fruitTransaction.getOperation());
        operationHandler.handle(fruitTransaction);
        Integer expected = 3;
        Integer actual = Storage.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @After
    public void clean() {
        Storage.fruits.clear();
    }
}

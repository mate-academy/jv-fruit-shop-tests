package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.FruitShopStrategy;
import core.basesyntax.strategy.impl.FruitShopStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static FruitShopStrategy fruitShopStrategy;

    @Before
    public void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        fruitShopStrategy = new FruitShopStrategyImpl(operationHandlerMap);
    }

    @Test
    public void purchaseOperation_purchaseIsLessThenBalance_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction("p", "banana", 81);
        try {
            fruitShopStrategy.get(fruitTransaction.getOperation()).handle(fruitTransaction);
        } catch (RuntimeException e) {
            return;
        }
        fail("The purchase cannot be completed");
    }

    @Test
    public void purchaseOperation_purchaseIsMoreThenBalance_Ok() {
        Storage.storage.put("banana", 80);
        FruitTransaction fruitTransaction = new FruitTransaction("p", "banana", 60);
        fruitShopStrategy.get(fruitTransaction.getOperation()).handle(fruitTransaction);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }
}

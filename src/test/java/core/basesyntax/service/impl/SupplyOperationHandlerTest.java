package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

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

public class SupplyOperationHandlerTest {
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
    public void purchaseOperation_purchaseIsMoreThenBalance_Ok() {
        Storage.storage.put("banana", 80);
        FruitTransaction fruitTransaction = new FruitTransaction("s", "banana", 20);
        fruitShopStrategy.get(fruitTransaction.getOperation()).handle(fruitTransaction);
        int expected = 100;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }
}

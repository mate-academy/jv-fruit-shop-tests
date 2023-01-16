package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operationhandler.OperationHandler;
import core.basesyntax.strategy.operationhandler.impl.BalanceOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.ReturnOperationHandler;
import core.basesyntax.strategy.operationhandler.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        OperationHandler supplyOperationHandler = new SupplyOperationHandler();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, returnOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, supplyOperationHandler);
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Before
    public void beforeEach() {
        FruitStorage.fruitStorage.put("apple", 40);
    }

    @Test
    public void getHandler_balance_isOk() {
        Class<?> actual = strategy.getHandler(FruitTransaction.Operation.BALANCE).getClass();
        Class<?> expected = BalanceOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void getHandler_purchase_isOk() {
        Class<?> actual = strategy.getHandler(FruitTransaction.Operation.PURCHASE).getClass();
        Class<?> expected = PurchaseOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void getHandler_return_isOk() {
        Class<?> actual = strategy.getHandler(FruitTransaction.Operation.RETURN).getClass();
        Class<?> expected = ReturnOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void getHandler_supply_isOk() {
        Class<?> actual = strategy.getHandler(FruitTransaction.Operation.SUPPLY).getClass();
        Class<?> expected = SupplyOperationHandler.class;
        assertEquals(actual, expected);
    }
}

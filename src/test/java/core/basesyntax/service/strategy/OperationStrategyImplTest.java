package core.basesyntax.service.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        Map<Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(BALANCE, new BalanceOperationHandler(fruitDao));
        operationHandlersMap.put(SUPPLY, new SupplyOperationHandler(fruitDao));
        operationHandlersMap.put(PURCHASE, new PurchaseOperationHandler(fruitDao));
        operationHandlersMap.put(RETURN, new ReturnOperationHandler(fruitDao));
        strategy = new OperationStrategyImpl(operationHandlersMap);
    }

    @Test
    public void getBalanceHandler_Ok() {
        Class<? extends OperationHandler> actualHandler = strategy.get(BALANCE).getClass();
        Class<? extends OperationHandler> expectedHandler = BalanceOperationHandler.class;
        Assert.assertEquals(expectedHandler, actualHandler);
    }

    @Test
    public void getSupplyHandler_Ok() {
        Class<? extends OperationHandler> actualHandler = strategy.get(SUPPLY).getClass();
        Class<? extends OperationHandler> expectedHandler = SupplyOperationHandler.class;
        Assert.assertEquals(expectedHandler, actualHandler);
    }

    @Test
    public void getPurchaseHandler_Ok() {
        Class<? extends OperationHandler> actualHandler = strategy.get(PURCHASE).getClass();
        Class<? extends OperationHandler> expectedHandler = PurchaseOperationHandler.class;
        Assert.assertEquals(expectedHandler, actualHandler);
    }

    @Test
    public void getReturnHandler_Ok() {
        Class<? extends OperationHandler> actualHandler = strategy.get(RETURN).getClass();
        Class<? extends OperationHandler> expectedHandler = ReturnOperationHandler.class;
        Assert.assertEquals(expectedHandler, actualHandler);
    }

    @Test (expected = RuntimeException.class)
    public void getNonExistentOperation_NotOk() {
        Operation operation = null;
        strategy.get(operation).getClass();
    }
}

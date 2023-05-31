package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import core.basesyntax.strategy.handlers.ReturnOperationHandler;
import core.basesyntax.strategy.handlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Storage storage;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        storage = new StorageImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storage));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storage));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storage));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storage));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void operationStrategy_getBalanceOperation_OK() {
        Class actual = operationStrategy
                .getByOperation(FruitTransaction.Operation.BALANCE).getClass();
        Class expected = BalanceOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategy_getPurchaseOperation_OK() {
        Class actual = operationStrategy
                .getByOperation(FruitTransaction.Operation.PURCHASE).getClass();
        Class expected = PurchaseOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategy_getReturnOperation_OK() {
        Class actual = operationStrategy
                .getByOperation(FruitTransaction.Operation.RETURN).getClass();
        Class expected = ReturnOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategy_getSupplyOperation_OK() {
        Class actual = operationStrategy
                .getByOperation(FruitTransaction.Operation.SUPPLY).getClass();
        Class expected = SupplyOperationHandler.class;
        assertEquals(expected, actual);
    }
}

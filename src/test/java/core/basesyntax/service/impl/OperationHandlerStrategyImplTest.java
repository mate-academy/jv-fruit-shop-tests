package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private static OperationHandlerStrategy operationHandlerStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> strategyMap = new HashMap<>();
        StorageDao storageDao = new StorageDaoImpl();
        strategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(storageDao));
        strategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(storageDao));
        strategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(storageDao));
        strategyMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler(storageDao));
        operationHandlerStrategy = new OperationHandlerStrategyImpl(strategyMap);
    }

    @Test
    public void getBalanceOperation_ok() {
        assertEquals(BalanceHandler.class,
                operationHandlerStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    public void getSupplyOperation_ok() {
        assertEquals(SupplyHandler.class,
                operationHandlerStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    public void getPurchaseOperator() {
        assertEquals(PurchaseHandler.class,
                operationHandlerStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    public void getReturnOperator() {
        assertEquals(ReturnHandler.class,
                operationHandlerStrategy.get(FruitTransaction.Operation.RETURN).getClass());
    }
}

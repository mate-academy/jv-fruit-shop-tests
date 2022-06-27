package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitShopTransactions;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<FruitShopTransactions.Operation, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(FruitShopTransactions.Operation.BALANCE, new BalanceHandler(storageDao));
        strategyMap.put(FruitShopTransactions.Operation.SUPPLY, new SupplyHandler(storageDao));
        strategyMap.put(FruitShopTransactions.Operation.PURCHASE, new PurchaseHandler(storageDao));
        strategyMap.put(FruitShopTransactions.Operation.RETURN, new ReturnHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(strategyMap);
    }

    @Test
    public void getBalanceOperation_ok() {
        assertEquals(BalanceHandler.class,
                operationStrategy
                        .getOperationHandler(FruitShopTransactions.Operation.BALANCE)
                        .getClass());
    }

    @Test
    public void getSupplyOperation_ok() {
        assertEquals(SupplyHandler.class,
                operationStrategy
                        .getOperationHandler(FruitShopTransactions.Operation.SUPPLY)
                        .getClass());
    }

    @Test
    public void getPurchaseOperator_ok() {
        assertEquals(PurchaseHandler.class,
                operationStrategy
                        .getOperationHandler(FruitShopTransactions.Operation.PURCHASE)
                        .getClass());
    }

    @Test
    public void getReturnOperator_ok() {
        assertEquals(ReturnHandler.class,
                operationStrategy
                        .getOperationHandler(FruitShopTransactions.Operation.RETURN)
                        .getClass());
    }
}

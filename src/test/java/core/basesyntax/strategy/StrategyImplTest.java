package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.BalanceOperationHandlerImpl;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.service.impl.ReturnOperationHandlerImpl;
import core.basesyntax.service.impl.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class StrategyImplTest {
    private static StorageDao storageDao;
    private static FruitService fruitService;
    private static Map<FruitOperation.Operation, OperationHandler> operationHandlerMap;
    private static Strategy strategy;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        fruitService = new FruitServiceImpl(storageDao);
        operationHandlerMap = new HashMap<>();
        strategy = new StrategyImpl(operationHandlerMap);
        operationHandlerMap.put(FruitOperation.Operation.BALANCE,
                new BalanceOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(fruitService));
        operationHandlerMap.put(FruitOperation.Operation.RETURN,
                new ReturnOperationHandlerImpl(fruitService));
    }

    @Test
    public void getHandler_Balance_Ok() {
        OperationHandler actualBalance = strategy.get(FruitOperation
                .Operation.BALANCE);
        OperationHandler expectedBalance = new BalanceOperationHandlerImpl(fruitService);
        assertEquals(expectedBalance.getClass(), actualBalance.getClass());
    }

    @Test
    public void getHandler_Purchase_Ok() {
        OperationHandler actualPurchase = strategy.get(FruitOperation
                .Operation.PURCHASE);
        OperationHandler expectedPurchase = new PurchaseOperationHandlerImpl(fruitService);
        assertEquals(expectedPurchase.getClass(), actualPurchase.getClass());
    }

    @Test
    public void getHandler_Supply_Ok() {
        OperationHandler actualSupply = strategy.get(FruitOperation
                .Operation.SUPPLY);
        OperationHandler expectedSupply = new SupplyOperationHandlerImpl(fruitService);
        assertEquals(expectedSupply.getClass(), actualSupply.getClass());
    }

    @Test
    public void getHandler_Return_Ok() {
        OperationHandler actualReturn = strategy.get(FruitOperation
                .Operation.RETURN);
        OperationHandler expectedReturn = new ReturnOperationHandlerImpl(fruitService);
        assertEquals(expectedReturn.getClass(), actualReturn.getClass());
    }
}

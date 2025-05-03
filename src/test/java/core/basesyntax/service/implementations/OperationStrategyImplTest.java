package core.basesyntax.service.implementations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseOperationHandler;
import core.basesyntax.service.operationhandler.ReturnOperationHandler;
import core.basesyntax.service.operationhandler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private OperationStrategy operationStrategy;
    private OperationHandler operationHandler;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap;

    @Before
    public void setUp() {
        operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
    }

    @Test
    public void get_ok() {
        operationHandler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class,
                                operationHandler.getClass());
        operationHandler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(ReturnOperationHandler.class,
                                operationHandler.getClass());
        operationHandler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class,
                                operationHandler.getClass());
        operationHandler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class,
                                operationHandler.getClass());
    }

    @Test
    public void get_nullOperation_ok() {
        operationHandler = operationStrategy.get(null);
        assertNull(operationHandler);
    }
}

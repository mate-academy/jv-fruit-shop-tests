package core.basesyntax.service.implementations;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private StorageDao storageDao;
    private OperationStrategy operationStrategy;
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
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
        Assertions.assertEquals(BalanceOperationHandler.class,
                                operationHandler.getClass());
        operationHandler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(ReturnOperationHandler.class,
                                operationHandler.getClass());
        operationHandler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(PurchaseOperationHandler.class,
                                operationHandler.getClass());
        operationHandler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals(SupplyOperationHandler.class,
                                operationHandler.getClass());
    }

    @Test
    public void get_nullOperation_ok() {
        operationHandler = operationStrategy.get(null);
        Assertions.assertNull(operationHandler);
    }
}

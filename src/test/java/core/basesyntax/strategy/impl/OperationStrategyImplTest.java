package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.bd.dao.StorageDao;
import core.basesyntax.bd.dao.impl.StorageDaoImpl;
import core.basesyntax.service.operationhandler.Operation;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.impl.AddOperation;
import core.basesyntax.service.operationhandler.impl.SubtractOperation;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static StorageDao storageDao;
    private static Map<Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationHandlerMap.put(Operation.BALANCE, new AddOperation(storageDao));
        operationHandlerMap.put(Operation.SUPPLY, new AddOperation(storageDao));
        operationHandlerMap.put(Operation.PURCHASE, new SubtractOperation(storageDao));
        operationHandlerMap.put(Operation.RETURN, new AddOperation(storageDao));
    }

    @Test
    public void get_operationBalance_Ok() {
        assertEquals(operationStrategy.get(Operation.get("b")).getClass(),
                AddOperation.class);
    }

    @Test
    public void get_operationSupply_Ok() {
        assertEquals(operationStrategy.get(Operation.get("s")).getClass(),
                AddOperation.class);
    }

    @Test
    public void get_operationReturn_Ok() {
        assertEquals(operationStrategy.get(Operation.get("r")).getClass(),
                AddOperation.class);
    }

    @Test
    public void get_operationPurchase_Ok() {
        assertEquals(operationStrategy.get(Operation.get("p")).getClass(),
                SubtractOperation.class);
    }
}

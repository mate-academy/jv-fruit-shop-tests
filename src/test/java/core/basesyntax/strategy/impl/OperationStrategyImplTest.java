package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(storageDao));
    }

    @Test
    public void check_Supply_Operation_Ok() {
        Class expected = SupplyOperationHandlerImpl.class;
        Class actual = new OperationStrategyImpl(operationHandlerMap)
                .getOperationType(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void check_Balance_Operation_Ok() {
        Class expected = BalanceOperationHandlerImpl.class;
        Class actual = new OperationStrategyImpl(operationHandlerMap)
                .getOperationType(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void check_Return_Operation_Ok() {
        Class expected = ReturnOperationHandlerImpl.class;
        Class actual = new OperationStrategyImpl(operationHandlerMap)
                .getOperationType(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected,actual);
    }

    @Test
    public void check_Purchase_Operation_Ok() {
        Class expected = PurchaseOperationHandlerImpl.class;
        Class actual = new OperationStrategyImpl(operationHandlerMap)
                .getOperationType(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected,actual);
    }
}

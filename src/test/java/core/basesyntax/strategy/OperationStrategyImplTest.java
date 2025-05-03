package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.operations.BalanceOperationHandlerImpl;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandlerImpl;
import core.basesyntax.service.operations.ReturnOperationHandlerImpl;
import core.basesyntax.service.operations.SupplyOperationHandlerImpl;
import core.basesyntax.template.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationsHandlerMap;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationsHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationsHandlerMap);
    }

    @Before
    public void setUp() {
        StorageDao storageDao = new StorageDaoImpl();
        operationsHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(storageDao));
        operationsHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(storageDao));
        operationsHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandlerImpl(storageDao));
        operationsHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(storageDao));
    }

    @Test
    public void getNullInputFruitTransactionOperationType_NotOk() {
        try {
            operationStrategy.get(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown "
                + "for null input FruitTransaction operation type");
    }

    @Test
    public void getReturnCorrectOperationHandler_oK() {
        assertEquals(PurchaseOperationHandlerImpl.class, operationStrategy
                .get(FruitTransaction.Operation.PURCHASE)
                .getClass());
    }
}

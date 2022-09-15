package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void balanceOperationTest_Ok() {
        Class<BalanceOperationHandlerImpl> expected = BalanceOperationHandlerImpl.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperationTest_Ok() {
        Class<PurchaseOperationHandlerImpl> expected = PurchaseOperationHandlerImpl.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void returnOperationTest_Ok() {
        Class<ReturnOperationHandlerImpl> expected = ReturnOperationHandlerImpl.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void supplyOperationTest_Ok() {
        Class<SupplyOperationHandlerImpl> expected = SupplyOperationHandlerImpl.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }
}

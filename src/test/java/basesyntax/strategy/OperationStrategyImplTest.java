package basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import basesyntax.dao.StorageDao;
import basesyntax.dao.StorageDaoImpl;
import basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
                new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandlerImpl(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandlerImpl(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperation_ok() {
        assertEquals(
                operationStrategy.getOperationType(FruitTransaction.Operation.BALANCE).getClass(),
                BalanceOperationHandlerImpl.class);
    }

    @Test
    public void get_purchaseOperation_ok() {
        assertEquals(
                operationStrategy.getOperationType(FruitTransaction.Operation.PURCHASE).getClass(),
                PurchaseOperationHandlerImpl.class);
    }

    @Test
    public void get_supplyOperation_ok() {
        assertEquals(operationStrategy.getOperationType(FruitTransaction.Operation.SUPPLY)
                        .getClass(),
                SupplyOperationHandlerImpl.class);
    }

    @Test
    public void get_returnOperation_ok() {
        assertEquals(operationStrategy.getOperationType(FruitTransaction.Operation.RETURN)
                        .getClass(),
                ReturnOperationHandlerImpl.class);
    }
}

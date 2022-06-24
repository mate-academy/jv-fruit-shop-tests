package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
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
                new BalanceOperation(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void balanceOperator_is_ok() {
        Class<? extends OperationHandler> expected = BalanceOperation.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationType(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void supplyOperator_is_ok() {
        Class<? extends OperationHandler> expected = SupplyOperation.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationType(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperator_is_ok() {
        Class<? extends OperationHandler> expected = PurchaseOperation.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationType(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    public void returnOperator_is_ok() {
        Class<? extends OperationHandler> expected = ReturnOperation.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationType(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }
}

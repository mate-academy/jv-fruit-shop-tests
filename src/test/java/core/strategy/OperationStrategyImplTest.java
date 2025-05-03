package core.strategy;

import static org.junit.Assert.assertEquals;

import core.model.FruitTransaction;
import core.operations.BalanceOperation;
import core.operations.Operation;
import core.operations.PurchaseOperation;
import core.operations.ReturnOperation;
import core.operations.SupplyOperation;
import core.storage.Storage;
import core.storage.StorageImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Activity, Operation> activityOperationMap;
    private Storage defaultStorage;

    @Before
    public void setUp() {
        defaultStorage = new StorageImpl();
        activityOperationMap = new HashMap<>();
        activityOperationMap.put(FruitTransaction.Activity.BALANCE,
                new BalanceOperation(defaultStorage));
        activityOperationMap.put(FruitTransaction.Activity.PURCHASE,
                new PurchaseOperation(defaultStorage));
        activityOperationMap.put(FruitTransaction.Activity.RETURN,
                new ReturnOperation(defaultStorage));
        activityOperationMap.put(FruitTransaction.Activity.SUPPLY,
                new SupplyOperation(defaultStorage));
    }

    @Test
    public void operationStrategyImpl_returnBalanceOperation_Ok() {
        Class actual = new OperationStrategyImpl(activityOperationMap)
                .get(FruitTransaction.Activity.BALANCE).getClass();
        Class expected = BalanceOperation.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategyImpl_returnPurchaseOperation_Ok() {
        Class actual = new OperationStrategyImpl(activityOperationMap)
                .get(FruitTransaction.Activity.PURCHASE).getClass();
        Class expected = PurchaseOperation.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategyImpl_returnReturnOperation_Ok() {
        Class actual = new OperationStrategyImpl(activityOperationMap)
                .get(FruitTransaction.Activity.RETURN).getClass();
        Class expected = ReturnOperation.class;
        assertEquals(expected, actual);
    }

    @Test
    public void operationStrategyImpl_returnSupplyOperation_Ok() {
        Class actual = new OperationStrategyImpl(activityOperationMap)
                .get(FruitTransaction.Activity.SUPPLY).getClass();
        Class expected = SupplyOperation.class;
        assertEquals(expected, actual);
    }
}

package core.operations;

import static org.junit.Assert.assertEquals;

import core.model.FruitTransaction;
import core.storage.Storage;
import core.storage.StorageImpl;
import core.strategy.OperationStrategy;
import core.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationTest {
    private static final FruitTransaction BALANCE_100_APPLE =
            new FruitTransaction(FruitTransaction.Activity.BALANCE, "apple", 100);
    private static final FruitTransaction BALANCE_50_BANANA =
            new FruitTransaction(FruitTransaction.Activity.BALANCE, "banana", 50);
    private static final FruitTransaction PURCHASE_10_APPLE =
            new FruitTransaction(FruitTransaction.Activity.PURCHASE, "apple", 10);
    private static final FruitTransaction SUPPLY_100_BANANA =
            new FruitTransaction(FruitTransaction.Activity.SUPPLY, "banana", 100);
    private static final FruitTransaction RETURN_20_BANANA =
            new FruitTransaction(FruitTransaction.Activity.RETURN, "banana", 20);
    private OperationStrategy operationStrategy;
    private Storage storage;

    @Before
    public void setUp() {
        storage = new StorageImpl();
        Map<FruitTransaction.Activity, Operation> activityOperationMap = new HashMap<>();
        activityOperationMap.put(FruitTransaction.Activity.BALANCE,
                new BalanceOperation(storage));
        activityOperationMap.put(FruitTransaction.Activity.PURCHASE,
                new PurchaseOperation(storage));
        activityOperationMap.put(FruitTransaction.Activity.RETURN,
                new ReturnOperation(storage));
        activityOperationMap.put(FruitTransaction.Activity.SUPPLY,
                new SupplyOperation(storage));
        operationStrategy = new OperationStrategyImpl(activityOperationMap);
    }

    @Test
    public void operation_balanceApple100_Ok() {
        operationStrategy.get(BALANCE_100_APPLE.getActivity())
                .performOperation(BALANCE_100_APPLE);
        Integer expected = 100;
        Integer actual = storage.getAllData().get(BALANCE_100_APPLE.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    public void operation_balance50Banana_Ok() {
        operationStrategy.get(BALANCE_50_BANANA.getActivity())
                .performOperation(BALANCE_50_BANANA);
        Integer expected = 50;
        Integer actual = storage.getAllData().get(BALANCE_50_BANANA.getFruit());
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void operation_purchase10AppleFromEmptyStorage_NotOk() {
        operationStrategy.get(PURCHASE_10_APPLE.getActivity())
                .performOperation(BALANCE_50_BANANA);
    }

    @Test
    public void operation_return20Banana_Ok() {
        operationStrategy.get(RETURN_20_BANANA.getActivity())
                .performOperation(RETURN_20_BANANA);
        Integer actual = storage.getAllData().get(RETURN_20_BANANA.getFruit());
        Integer expected = 20;
        assertEquals(expected, actual);
    }

    @Test
    public void operation_supply100Banana_Ok() {
        operationStrategy.get(SUPPLY_100_BANANA.getActivity())
                .performOperation(SUPPLY_100_BANANA);
        Integer actual = storage.getAllData().get(SUPPLY_100_BANANA.getFruit());
        Integer expected = 100;
        assertEquals(expected, actual);
    }
}

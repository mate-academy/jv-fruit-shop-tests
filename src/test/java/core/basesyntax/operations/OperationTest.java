package core.basesyntax.operations;

import core.basesyntax.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.storage.StorageImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Map<FruitTransaction.Activity, Operation> activityOperationMap = new HashMap<>();
        activityOperationMap.put(FruitTransaction.Activity.BALANCE, new BalanceOperation());
        activityOperationMap.put(FruitTransaction.Activity.PURCHASE, new PurchaseOperation());
        activityOperationMap.put(FruitTransaction.Activity.RETURN, new ReturnOperation());
        activityOperationMap.put(FruitTransaction.Activity.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(activityOperationMap);
        storage = new StorageImpl();
    }

    @Test
    public void operation_balanceApple100_Ok() {
        operationStrategy.get(BALANCE_100_APPLE.getActivity())
                .performOperation(storage, BALANCE_100_APPLE);
        Integer expected = 100;
        Integer actual = storage.getAllData().get(BALANCE_100_APPLE.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operation_balance50Banana_Ok() {
        operationStrategy.get(BALANCE_50_BANANA.getActivity())
                .performOperation(storage, BALANCE_50_BANANA);
        Integer expected = 50;
        Integer actual = storage.getAllData().get(BALANCE_50_BANANA.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operation_purchase10AppleFromEmptyStorage_NotOk() {
        assertThrows(RuntimeException.class, () ->
                operationStrategy.get(PURCHASE_10_APPLE.getActivity())
                        .performOperation(storage, BALANCE_50_BANANA));
    }

    @Test
    public void operation_return20Banana_Ok() {
        operationStrategy.get(RETURN_20_BANANA.getActivity()).performOperation(storage, RETURN_20_BANANA);
        Integer actual = storage.getAllData().get(RETURN_20_BANANA.getFruit());
        Integer expected = 20;
        assertEquals(expected, actual);
    }

    @Test
    public void operation_supply100Banana_Ok() {
        operationStrategy.get(SUPPLY_100_BANANA.getActivity()).performOperation(storage, SUPPLY_100_BANANA);
        Integer actual = storage.getAllData().get(SUPPLY_100_BANANA.getFruit());
        Integer expected = 100;
        assertEquals(expected, actual);
    }
}

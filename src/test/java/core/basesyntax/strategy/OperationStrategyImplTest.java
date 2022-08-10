package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.Operation;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Activity, Operation> activityOperationMap;

    @Before
    public void setUp() {
        activityOperationMap = new HashMap<>();
        activityOperationMap.put(FruitTransaction.Activity.BALANCE, new BalanceOperation());
        activityOperationMap.put(FruitTransaction.Activity.PURCHASE, new PurchaseOperation());
        activityOperationMap.put(FruitTransaction.Activity.RETURN, new ReturnOperation());
        activityOperationMap.put(FruitTransaction.Activity.SUPPLY, new SupplyOperation());
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

package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE_CONST = "b";
    private static final String SUPPLY_CONST = "s";
    private static final String RETURN_CONST = "r";
    private static final String PURCHASE_CONST = "p";
    private static final String INCORRECT_DATA_INPU = "d";
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> defaultMap = new HashMap<>();
        defaultMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        defaultMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        defaultMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        defaultMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategy = new OperationStrategyImpl(defaultMap);
    }

    @Test
    public void getBalanceHandler_ok() {
        OperationHandler expected = new BalanceOperationHandler();
        Assert.assertEquals(expected.getClass(), operationStrategy
                .get(FruitTransaction.Operation.getByCode(BALANCE_CONST)).getClass());
    }

    @Test
    public void getReturnHandler_ok() {
        OperationHandler expected = new ReturnOperationHandler();
        Assert.assertEquals(expected.getClass(), operationStrategy
                .get(FruitTransaction.Operation.getByCode(RETURN_CONST)).getClass());
    }

    @Test
    public void getPurchaseHandler_ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        Assert.assertEquals(expected.getClass(), operationStrategy
                .get(FruitTransaction.Operation.getByCode(PURCHASE_CONST)).getClass());
    }

    @Test
    public void getSupplyHandler_ok() {
        OperationHandler expected = new SupplyOperationHandler();
        Assert.assertEquals(expected.getClass(), operationStrategy
                .get(FruitTransaction.Operation.getByCode(SUPPLY_CONST)).getClass());
    }

    @Test(expected = RuntimeException.class)
    public void getHandler_notOk() {
        operationStrategy.get(FruitTransaction.Operation.getByCode(INCORRECT_DATA_INPU));
    }
}

package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.operation.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.operation.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final Map<FruitTransaction.Operation,
            OperationHandler> STRATEGIES = new HashMap<>();
    private static OperationStrategyImpl operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        STRATEGIES.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl());
        STRATEGIES.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl());
        STRATEGIES.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        STRATEGIES.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl());
        operationStrategy = new OperationStrategyImpl(STRATEGIES);
    }

    @Test
    public void getBalanceOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assert.assertTrue(actual.getClass().equals(BalanceOperationHandlerImpl.class));
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assert.assertTrue(actual.getClass().equals(PurchaseOperationHandlerImpl.class));
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assert.assertTrue(actual.getClass().equals(ReturnOperationHandlerImpl.class));
    }

    @Test
    public void getSupplyOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assert.assertTrue(actual.getClass().equals(SupplyOperationHandlerImpl.class));
    }
}

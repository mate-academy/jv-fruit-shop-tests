package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.operation.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.operation.SupplyOperationHandlerImpl;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategyImpl operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> handlersMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl());
        operationStrategy = new OperationStrategyImpl(handlersMap);
    }

    @Test
    public void get_BalanceOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assert.assertTrue(actual.getClass().equals(BalanceOperationHandlerImpl.class));
    }

    @Test
    public void get_PurchaseOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assert.assertTrue(actual.getClass().equals(PurchaseOperationHandlerImpl.class));
    }

    @Test
    public void get_ReturnOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assert.assertTrue(actual.getClass().equals(ReturnOperationHandlerImpl.class));
    }

    @Test
    public void get_SupplyOperationHandler_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assert.assertTrue(actual.getClass().equals(SupplyOperationHandlerImpl.class));
    }
}

package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitTransaction;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy = new OperationStrategyImpl();

    @Test
    public void returnOperationHandlerReturn_Ok() {
        FruitOperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(FruitOperationHandlerReturn.class,actual.getClass());
    }

    @Test
    public void returnOperationHandlerBalance_Ok() {
        FruitOperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(FruitOperationHandlerBalance.class,actual.getClass());
    }

    @Test
    public void returnOperationHandlerPurchase_Ok() {
        FruitOperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(FruitOperationHandlerPurchase.class,actual.getClass());
    }

    @Test
    public void returnOperationHandlerSupply_Ok() {
        FruitOperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(FruitOperationHandlerSupply.class,actual.getClass());
    }
}


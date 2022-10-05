package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.service.BalanceOperationHandler;
import core.basesyntax.service.PurchaseOperationHandler;
import core.basesyntax.service.ReturnOperationHandler;
import core.basesyntax.service.SupplyOperationHandler;
import org.junit.Before;
import org.junit.Test;

public abstract class OperationStrategyTestBase<T extends OperationStrategy> {
    private T instance;

    protected abstract T createInstance();

    @Before
    public void setUp() {
        instance = createInstance();
    }

    @Test
    public void get_Purchase_Operation_Handler_ok() {
        assertEquals(PurchaseOperationHandler.class, instance.get(Operation.PURCHASE).getClass());
    }

    @Test
    public void get_Balance_Operation_Handler_ok() {
        assertEquals(BalanceOperationHandler.class, instance.get(Operation.BALANCE).getClass());
    }

    @Test
    public void get_Return_Operation_Handler_ok() {
        assertEquals(ReturnOperationHandler.class, instance.get(Operation.RETURN).getClass());
    }

    @Test
    public void get_Supply_Operation_Handler_ok() {
        assertEquals(SupplyOperationHandler.class, instance.get(Operation.SUPPLY).getClass());
    }
}
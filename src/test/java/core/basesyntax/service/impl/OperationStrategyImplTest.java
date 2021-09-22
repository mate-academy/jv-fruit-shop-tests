package core.basesyntax.service.impl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import core.basesyntax.service.strategy.ReturnHandler;
import core.basesyntax.service.strategy.SupplyHandler;

public class OperationStrategyImplTest {
    private static OperationStrategyImpl operationStrategy;
    private static final String[] BALANCE_ARRAY = new String[] {"b"};
    private static final String[] SUPPLY_ARRAY = new String[] {"s"};
    private static final String[] PURCHASE_ARRAY = new String[] {"p"};
    private static final String[] RETURN_ARRAY = new String[] {"r"};
    private static final String[] EMPTY_ARRAY = new String[] {""};

    @Before
    public void initialize() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void get_balance_Ok() {
        assertEquals(operationStrategy.get(BALANCE_ARRAY).getClass(), BalanceHandler.class);
    }

    @Test
    public void get_supply_Ok() {
        assertEquals(operationStrategy.get(SUPPLY_ARRAY).getClass(), SupplyHandler.class);
    }

    @Test
    public void get_purchase_Ok() {
        assertEquals(operationStrategy.get(PURCHASE_ARRAY).getClass(), PurchaseHandler.class);
    }

    @Test
    public void get_return_Ok() {
        assertEquals(operationStrategy.get(RETURN_ARRAY).getClass(), ReturnHandler.class);
    }

    @Test
    public void empty_array_get() {
        assertNull(operationStrategy.get(EMPTY_ARRAY));
    }
}
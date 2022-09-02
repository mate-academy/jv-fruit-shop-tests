package core.basesyntax.strategy;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void setup() {
        HashMap<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("r", new ReturnOperationHandler());
        strategy = new OperationStrategy(map);
    }

    @Test
    public void strategy_returns_balance_operation_handler_OK() {
        OperationHandler subClass = strategy.getByOperation("b");
        assertThat(subClass, instanceOf(BalanceOperationHandler.class));
    }

    @Test
    public void strategy_returns_purchase_operation_handler_OK() {
        OperationHandler subClass = strategy.getByOperation("p");
        assertThat(subClass, instanceOf(PurchaseOperationHandler.class));
    }

    @Test
    public void strategy_returns_return_operation_handler_OK() {
        OperationHandler subClass = strategy.getByOperation("r");
        assertThat(subClass, instanceOf(ReturnOperationHandler.class));
    }

    @Test
    public void strategy_returns_supply_operation_handler_OK() {
        OperationHandler subClass = strategy.getByOperation("s");
        assertThat(subClass, instanceOf(SupplyOperationHandler.class));
    }
}

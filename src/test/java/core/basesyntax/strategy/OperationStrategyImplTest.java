package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.service.BalanceOperationHandler;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.PurchaseOperationHandler;
import core.basesyntax.service.ReturnOperationHandler;
import core.basesyntax.service.SupplyOperationHandler;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, OperationHandler> operations = Map.of(
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler()
        );
        strategy = new OperationStrategyImpl(operations);
    }

    @Test
    public void get_Purchase_Operation_Handler_ok() {
        assertEquals(PurchaseOperationHandler.class, strategy.get(Operation.PURCHASE).getClass());
    }

    @Test
    public void get_Balance_Operation_Handler_ok() {
        assertEquals(BalanceOperationHandler.class, strategy.get(Operation.BALANCE).getClass());
    }

    @Test
    public void get_Return_Operation_Handler_ok() {
        assertEquals(ReturnOperationHandler.class, strategy.get(Operation.RETURN).getClass());
    }

    @Test
    public void get_Supply_Operation_Handler_ok() {
        assertEquals(SupplyOperationHandler.class, strategy.get(Operation.SUPPLY).getClass());
    }
}

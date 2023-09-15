package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.operations.BalanceOperationHandler;
import core.basesyntax.service.impl.operations.PurchaseOperationHandler;
import core.basesyntax.service.impl.operations.ReturnOperationHandler;
import core.basesyntax.service.impl.operations.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OperationStrategyImplTest {
    private static OperationStrategyImpl strategy;
    @BeforeAll
    static void setUp() {
        Map<Operation, OperationHandler> operationsWithHandlers = new HashMap<>();

        operationsWithHandlers.put(Operation.BALANCE, new BalanceOperationHandler());
        operationsWithHandlers.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationsWithHandlers.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationsWithHandlers.put(Operation.RETURN, new ReturnOperationHandler());

        strategy = new OperationStrategyImpl(operationsWithHandlers);
    }

    @Test
    void get_nullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> strategy.get(null));
    }

    @Test
    void get_ok() {
        BalanceOperationHandler balance = new BalanceOperationHandler();
        assertEquals(balance.getClass(), strategy.get(Operation.BALANCE).getClass());
        SupplyOperationHandler supply = new SupplyOperationHandler();
        assertEquals(supply.getClass(), strategy.get(Operation.SUPPLY).getClass());
        PurchaseOperationHandler purchase = new PurchaseOperationHandler();
        assertEquals(purchase.getClass(), strategy.get(Operation.PURCHASE).getClass());
        ReturnOperationHandler returnOperation = new ReturnOperationHandler();
        assertEquals(returnOperation.getClass(), strategy.get(Operation.RETURN).getClass());
    }
}
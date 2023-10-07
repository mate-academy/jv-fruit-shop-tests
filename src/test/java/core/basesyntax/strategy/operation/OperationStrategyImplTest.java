package core.basesyntax.strategy.operation;

import static core.basesyntax.model.Operation.BALANCE;
import static core.basesyntax.model.Operation.PURCHASE;
import static core.basesyntax.model.Operation.RETURN;
import static core.basesyntax.model.Operation.SUPPLY;
import static core.basesyntax.model.Operation.UNKNOWN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private final OperationStrategy strategy = new OperationStrategyImpl();

    @Test
    void get_isOk() {
        final OperationHandler balanceHandler = strategy.get(BALANCE);
        final OperationHandler purchaseHandler = strategy.get(PURCHASE);
        final OperationHandler returnHandler = strategy.get(RETURN);
        final OperationHandler supplyHandler = strategy.get(SUPPLY);
        assertTrue(balanceHandler instanceof BalanceImpl, "balanceHandler must be instance of "
                + BalanceImpl.class.getSimpleName());
        assertTrue(purchaseHandler instanceof PurchaseImpl, "purchaseHandler must be instance of "
                + PurchaseImpl.class.getSimpleName());
        assertTrue(returnHandler instanceof ReturnImpl, "returnHandler must be instance of "
                + ReturnImpl.class.getSimpleName());
        assertTrue(supplyHandler instanceof SupplyImpl, "supplyHandler must be instance of "
                + SupplyImpl.class.getSimpleName());
    }

    @Test
    void get_wrongOperationType_notOk() {
        assertThrows(RuntimeException.class, () -> strategy.get(UNKNOWN),
                "Must throws RuntimeException.");
        assertThrows(RuntimeException.class, () -> strategy.get(null),
                "null Operation must throws RuntimeException.");
    }
}

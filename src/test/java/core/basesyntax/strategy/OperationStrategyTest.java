package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.service.impl.BalanceTransactionHandler;
import core.basesyntax.service.impl.PurchaseTransactionHandler;
import core.basesyntax.service.impl.ReturnTransactionHandler;
import core.basesyntax.service.impl.SupplyTransactionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OperationStrategyTest {
    private static final String UNKNOWN_OPERATION = "n";
    private OperationStrategy strategy;
    private Map<String, TransactionHandler> operationMap;

    @BeforeEach
    void setUp() {
        operationMap = Map.of(
                Operation.BALANCE.getCode(), new BalanceTransactionHandler(),
                Operation.SUPPLY.getCode(), new SupplyTransactionHandler(),
                Operation.PURCHASE.getCode(), new PurchaseTransactionHandler(),
                Operation.RETURN.getCode(), new ReturnTransactionHandler());
        strategy = new OperationStrategy(operationMap);
    }

    @Test
    void balanceOperation_ok() {
        TransactionHandler actual = strategy.getOperationHandler(Operation.BALANCE);
        assertEquals(BalanceTransactionHandler.class, actual.getClass());
    }

    @Test
    void unknownOperation_notOk() {
        assertThrows(RuntimeException.class, () -> strategy.getOperationHandler(Operation.getOperationFromCode(UNKNOWN_OPERATION)));
    }
}
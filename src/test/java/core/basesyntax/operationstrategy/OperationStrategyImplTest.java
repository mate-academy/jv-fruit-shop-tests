package core.basesyntax.operationstrategy;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operationservice.BalanceOperationImpl;
import core.basesyntax.operationservice.OperationHandler;
import core.basesyntax.operationservice.PurchaseOperationImpl;
import core.basesyntax.operationservice.ReturnOperationImpl;
import core.basesyntax.operationservice.SupplyOperationImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationImpl());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationImpl());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationImpl());

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_validOperation_returnsCorrectHandler() {
        assertTrue(operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE) instanceof BalanceOperationImpl);
        assertTrue(operationStrategy.getHandler(
                FruitTransaction.Operation.SUPPLY) instanceof SupplyOperationImpl);
        assertTrue(operationStrategy.getHandler(
                FruitTransaction.Operation.PURCHASE) instanceof PurchaseOperationImpl);
        assertTrue(operationStrategy.getHandler(
                FruitTransaction.Operation.RETURN) instanceof ReturnOperationImpl);
    }

    @Test
    void getHandler_nullOperation_throwsException() {
        assertThrows(NullPointerException.class, () -> operationStrategy.getHandler(null));
    }
}


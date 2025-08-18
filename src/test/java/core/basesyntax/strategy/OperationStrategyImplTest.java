package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_balanceOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertInstanceOf(BalanceOperation.class, handler);
    }

    @Test
    void get_supplyOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertInstanceOf(SupplyOperation.class, handler);
    }

    @Test
    void get_purchaseOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertInstanceOf(PurchaseOperation.class, handler);
    }

    @Test
    void get_returnOperation_ok() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertInstanceOf(ReturnOperation.class, handler);
    }

    @Test
    void get_invalidOperation_throwException() {
        assertThrows(IllegalArgumentException.class, () -> operationStrategy.get(null));
    }
}

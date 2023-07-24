package strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    void getBalanceOperation_ok() {
        new BalanceOperationStrategy();
        OperationHandler handler;
        handler = operationStrategy.get(Operation.BALANCE);

        assertTrue(handler instanceof BalanceOperationStrategy,
                "The handler should be an instance of BalanceOperationStrategy");
    }

    @Test
    void getSupplyOperation_ok() {
        new SupplyOperationStrategy();
        OperationHandler handler;
        handler = operationStrategy.get(Operation.SUPPLY);

        assertTrue(handler instanceof SupplyOperationStrategy,
                "The handler should be an instance of SupplyOperationStrategy");
    }

    @Test
    void getPurchaseOperation_ok() {
        new PurchaseOperationStrategy();
        OperationHandler handler;
        handler = operationStrategy.get(Operation.PURCHASE);

        assertTrue(handler instanceof PurchaseOperationStrategy,
                "The handler should be an instance of PurchaseOperationStrategy");
    }

    @Test
    void getReturnOperation_ok() {
        new ReturnOperationStrategy();
        OperationHandler handler;
        handler = operationStrategy.get(Operation.RETURN);

        assertTrue(handler instanceof ReturnOperationStrategy,
                "The handler should be an instance of ReturnOperationStrategy");
    }
}

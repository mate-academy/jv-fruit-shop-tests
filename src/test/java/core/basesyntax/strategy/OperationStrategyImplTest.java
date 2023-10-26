package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.PurchaseOperationHandler;
import core.basesyntax.service.RemnantOperationHandler;
import core.basesyntax.service.SupplyOperationHandler;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(new HashMap<>() {{
                put(FruitTransaction.Operation.BALANCE, new RemnantOperationHandler());
                put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
                put(FruitTransaction.Operation.RETURN, new SupplyOperationHandler());
                put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
            }}
        );
    }

    @Test
    void getBalanceOperationHandler_Ok() {
        assertTrue(operationStrategy.get(FruitTransaction.Operation.BALANCE)
                instanceof RemnantOperationHandler);
    }

    @Test
    void getSupplyOperationHandler_Ok() {
        assertTrue(operationStrategy.get(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperationHandler);
    }

    @Test
    void getPurchaseOperationHandler_Ok() {
        assertTrue(operationStrategy.get(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseOperationHandler);
    }

    @Test
    void getReturnOperationHandler_Ok() {
        assertTrue(operationStrategy.get(FruitTransaction.Operation.RETURN)
                instanceof SupplyOperationHandler);
    }
}

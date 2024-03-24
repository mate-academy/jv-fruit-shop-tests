package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.operations.BalanceStrategy;
import core.basesyntax.service.strategy.operations.PurchaseStrategy;
import core.basesyntax.service.strategy.operations.ReturnStrategy;
import core.basesyntax.service.strategy.operations.SupplyStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StrategyTest {
    private static final String BALANCE_LITERAL = "b";
    private static final String SUPPLY_LITERAL = "s";
    private static final String RETURN_LITERAL = "r";
    private static final String PURCHASE_LITERAL = "p";
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> services;

    @BeforeAll
    static void initAll() {
        services = new HashMap<>();
        services.put(BALANCE_LITERAL, new BalanceStrategy());
        services.put(SUPPLY_LITERAL, new SupplyStrategy());
        services.put(RETURN_LITERAL, new ReturnStrategy());
        services.put(PURCHASE_LITERAL, new PurchaseStrategy());

        operationStrategy = new OperationStrategy(services);
    }

    @Test
    void getOperation_balanceOperation_Ok() {
        OperationHandler result = operationStrategy.getOperation(Operation.BALANCE);
        assertEquals(BalanceStrategy.class, result.getClass(),
                "We need to have Balance operation.");
    }

    @Test
    void getOperation_purchaseOperation_Ok() {
        OperationHandler result = operationStrategy.getOperation(Operation.PURCHASE);
        assertEquals(PurchaseStrategy.class, result.getClass(),
                "We need to have Purchase operation.");
    }

    @Test
    void getOperation_returnOperation_Ok() {
        OperationHandler result = operationStrategy.getOperation(Operation.RETURN);
        assertEquals(ReturnStrategy.class, result.getClass(),
                "We need to have Return operation.");
    }

    @Test
    void getOperation_supplyOperation_Ok() {
        OperationHandler result = operationStrategy.getOperation(Operation.SUPPLY);
        assertEquals(SupplyStrategy.class, result.getClass(),
                "We need to have Supply operation.");
    }

    @Test
    void getOperation_nullOperation_notOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperation(null),
                "We can't get null operation");
    }
}

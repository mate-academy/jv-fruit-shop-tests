package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.OperationStrategy;
import service.impl.OperationStrategyImpl;
import service.strategy.OperationHandler;
import service.strategy.hendlers.BalanceOperationHandler;
import service.strategy.hendlers.PurchaseOperationHandler;
import service.strategy.hendlers.ReturnOperationHandler;
import service.strategy.hendlers.SupplyOperationHandler;

public class OperationStrategyImplTest {
    private static OperationHandler expected;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        expected = new SupplyOperationHandler();
        operationStrategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY, expected));
    }

    @Test
    void getOperationHandler_findHandler_ok() {
        OperationHandler actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected, actual);
    }
}


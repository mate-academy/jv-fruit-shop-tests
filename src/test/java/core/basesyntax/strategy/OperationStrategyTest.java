package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    public static void setup() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
        FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(),
        FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(),
        FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(),
        FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    public void get_balanceOperation_ok() {
        FruitTransaction.Operation balanceOperation = FruitTransaction.Operation.BALANCE;
        assertDoesNotThrow(() -> operationStrategy.get(balanceOperation));
    }

    @Test
    public void get_returnOperation_ok() {
        FruitTransaction.Operation returnOperation = FruitTransaction.Operation.RETURN;
        assertDoesNotThrow(() -> operationStrategy.get(returnOperation));
    }

    @Test
    public void get_purchaseOperation_ok() {
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.PURCHASE;
        assertDoesNotThrow(() -> operationStrategy.get(purchaseOperation));
    }

    @Test
    public void get_supplyOperation_ok() {
        FruitTransaction.Operation supplyOperation = FruitTransaction.Operation.SUPPLY;
        assertDoesNotThrow(() -> operationStrategy.get(supplyOperation));
    }
}

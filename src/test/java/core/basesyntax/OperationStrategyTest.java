package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
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
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperation_Ok() {
        FruitTransaction.Operation balanceOperation = FruitTransaction.Operation.BALANCE;
        assertDoesNotThrow(() -> operationStrategy.get(balanceOperation));
    }

    @Test
    public void get_returnOperation_Ok() {
        FruitTransaction.Operation returnOperation = FruitTransaction.Operation.RETURN;
        assertDoesNotThrow(() -> operationStrategy.get(returnOperation));
    }

    @Test
    public void get_purchaseOperation_Ok() {
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.PURCHASE;
        assertDoesNotThrow(() -> operationStrategy.get(purchaseOperation));
    }

    @Test
    public void get_supplyOperation_Ok() {
        FruitTransaction.Operation supplyOperation = FruitTransaction.Operation.SUPPLY;
        assertDoesNotThrow(() -> operationStrategy.get(supplyOperation));
    }
}

package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction
                .Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_operationHandlerCorrespondsToTheOperation_Ok() {
        OperationHandler actualBalance = operationStrategy
                .get(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperation.class, actualBalance.getClass());
        OperationHandler actualPurchase = operationStrategy
                .get(FruitTransaction.Operation.PURCHASE);
        assertEquals(PurchaseOperation.class, actualPurchase.getClass());
        OperationHandler actualSupply = operationStrategy
                .get(FruitTransaction.Operation.SUPPLY);
        assertEquals(SupplyOperation.class, actualSupply.getClass());
        OperationHandler actualReturn = operationStrategy
                .get(FruitTransaction.Operation.RETURN);
        assertEquals(ReturnOperation.class, actualReturn.getClass());
    }
}

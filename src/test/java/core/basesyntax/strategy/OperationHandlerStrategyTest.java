package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerStrategyTest {
    private OperationHandlerStrategy operationHandlerStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandlerImpl(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandlerImpl(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandlerImpl(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandlerImpl());

    @BeforeEach
    void setUp() {
        operationHandlerStrategy = new OperationHandlerStrategyImpl(operationHandlerMap);
    }

    @Test
    void getOperationStrategy_supplyOperation_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        assertEquals(SupplyOperationHandlerImpl.class,
                operationHandlerStrategy.getOperationStrategy(operation).getClass());
    }

    @Test
    void getOperationStrategy_balanceOperation_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        assertEquals(BalanceOperationHandlerImpl.class,
                operationHandlerStrategy.getOperationStrategy(operation).getClass());
    }

    @Test
    void getOperationStrategy_returnOperation_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.RETURN;
        assertEquals(ReturnOperationHandlerImpl.class,
                operationHandlerStrategy.getOperationStrategy(operation).getClass());
    }

    @Test
    void getOperationStrategy_purchaseOperation_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        assertEquals(PurchaseOperationHandlerImpl.class,
                operationHandlerStrategy.getOperationStrategy(operation).getClass());
    }
}

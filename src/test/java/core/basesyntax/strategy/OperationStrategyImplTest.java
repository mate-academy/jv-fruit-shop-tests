package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_OperationTypeBalance_Ok() {
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected.getClass(), actual.getClass(), "BalanceOperationHandler is expected");
    }

    @Test
    void get_OperationTypeSupply_Ok() {
        OperationHandler expected = new SupplyOperationHandler();
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected.getClass(), actual.getClass(), "SupplyOperationHandler is expected");
    }

    @Test
    void get_OperationTypePurchase_Ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expected.getClass(), actual.getClass(),
                "PurchaseOperationHandler is expected");
    }

    @Test
    void get_OperationTypeReturn_Ok() {
        OperationHandler expected = new ReturnOperationHandler();
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(expected.getClass(), actual.getClass(), "ReturnOperationHandler is expected");
    }
}

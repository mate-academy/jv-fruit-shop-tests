package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operationhandlers.BalanceOperationHandler;
import core.basesyntax.strategy.operationhandlers.OperationHandler;
import core.basesyntax.strategy.operationhandlers.PurchaseOperationHandler;
import core.basesyntax.strategy.operationhandlers.ReturnOperationHandler;
import core.basesyntax.strategy.operationhandlers.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static final Map<FruitTransaction.Operation,
            OperationHandler> OPERATION_HANDLER_MAP = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(OPERATION_HANDLER_MAP);
    }

    @Test
    void get_balanceOperation_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        OperationHandler expected = new BalanceOperationHandler();
        assertEquals(expected, actual);
    }

    @Test
    void get_supplyOperation_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        OperationHandler expected = new SupplyOperationHandler();
        assertEquals(expected, actual);
    }

    @Test
    void get_purchaseOperation_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        OperationHandler expected = new PurchaseOperationHandler();
        assertEquals(expected, actual);
    }

    @Test
    void get_returnOperation_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        OperationHandler expected = new ReturnOperationHandler();
        assertEquals(expected, actual);
    }
}

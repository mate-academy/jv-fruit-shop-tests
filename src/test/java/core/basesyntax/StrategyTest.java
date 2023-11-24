package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operators.BalanceOperationHandler;
import core.basesyntax.operators.OperationHandler;
import core.basesyntax.operators.PurchaseOperationHandler;
import core.basesyntax.operators.ReturnOperationHandler;
import core.basesyntax.operators.SupplyOperationHandler;
import core.basesyntax.service.OperatorStrategy;
import core.basesyntax.service.impl.OperatorStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrategyTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationToHandlerMap
            = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    private OperatorStrategy operatorStrategy;

    @BeforeEach
    public void setUp() {
        operatorStrategy = new OperatorStrategyImpl(operationToHandlerMap);
    }

    @Test
    void getOperatorHandler_balanceOperation_ok() {
        OperationHandler handler
                = operatorStrategy.getOperatorHandler(FruitTransaction.Operation.BALANCE);
        Assertions.assertTrue(
                handler instanceof BalanceOperationHandler,
                "Handler should be BalanceOperationHandler for BALANCE operation");
    }

    @Test
    void getOperatorHandler_purchaseOperation_ok() {
        OperationHandler handler
                = operatorStrategy.getOperatorHandler(FruitTransaction.Operation.PURCHASE);
        Assertions.assertTrue(handler instanceof PurchaseOperationHandler,
                "Handler should be PurchaseOperationHandler for PURCHASE operation");
    }

    @Test
    void getOperatorHandler_returnOperation_ok() {
        OperationHandler handler
                = operatorStrategy.getOperatorHandler(FruitTransaction.Operation.RETURN);
        Assertions.assertTrue(handler instanceof ReturnOperationHandler,
                "Handler should be ReturnOperationHandler for RETURN operation");
    }

    @Test
    void getOperatorHandler_supplyOperation_ok() {
        OperationHandler handler
                = operatorStrategy.getOperatorHandler(FruitTransaction.Operation.SUPPLY);
        Assertions.assertTrue(handler instanceof SupplyOperationHandler,
                "Handler should be SupplyOperationHandler for SUPPLY operation");
    }
}

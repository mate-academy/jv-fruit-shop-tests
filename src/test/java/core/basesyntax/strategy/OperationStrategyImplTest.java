package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.handler.BalanceOperationHandler;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseHandler;
import core.basesyntax.service.handler.ReturnHandler;
import core.basesyntax.service.handler.SupplyHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getOperationHandler_null_notOk() {
        assertThrows(RuntimeException.class, () -> operationStrategy.getOperationHandler(null));
    }

    @Test
    public void getOperationHandler_balanceHandler_ok() {
        Assertions.assertEquals(BalanceOperationHandler.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE)
                        .getClass());
    }

    @Test
    public void getOperationHandler_supplyHandler_ok() {
        Assertions.assertEquals(SupplyHandler.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.SUPPLY)
                        .getClass());
    }

    @Test
    public void getOperationHandler_returnHandler_ok() {
        Assertions.assertEquals(ReturnHandler.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.RETURN)
                        .getClass());
    }

    @Test
    public void getOperationHandler_purchaseHandler_ok() {
        Assertions.assertEquals(PurchaseHandler.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.PURCHASE)
                        .getClass());
    }
}

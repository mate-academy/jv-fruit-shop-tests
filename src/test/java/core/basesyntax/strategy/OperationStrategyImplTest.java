package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final Map<Operation, OperationHandler> operation = Map.of(
            Operation.BALANCE, new BalanceOperationHandler(),
            Operation.RETURN, new ReturnOperationHandler(),
            Operation.PURCHASE, new PurchaseOperationHandler(),
            Operation.SUPPLY, new SupplyOperationHandler());
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(operation);

    @Test
    void getOperationHandler_balanceOperation_ok() {
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(Operation.BALANCE).getClass();
        Assertions.assertEquals(BalanceOperationHandler.class, actual);
    }

    @Test
    void getOperationHandler_purchaseOperation_ok() {
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(Operation.PURCHASE).getClass();
        Assertions.assertEquals(PurchaseOperationHandler.class, actual);
    }

    @Test
    void getOperationHandler_returnOperation_ok() {
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(Operation.RETURN).getClass();
        Assertions.assertEquals(ReturnOperationHandler.class, actual);
    }

    @Test
    void getOperationHandler_supplyOperation_ok() {
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(Operation.SUPPLY).getClass();
        Assertions.assertEquals(SupplyOperationHandler.class, actual);
    }
}

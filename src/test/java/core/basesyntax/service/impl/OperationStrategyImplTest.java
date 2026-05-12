package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    @Test
    void getHandlerExistingOperationReturnsHandler() {
        OperationHandler balanceHandler = new BalanceOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();
        OperationStrategyImpl operationStrategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, balanceHandler,
                FruitTransaction.Operation.PURCHASE, purchaseHandler
        ));

        assertSame(balanceHandler,
                operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
        assertSame(purchaseHandler,
                operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE));
    }
}

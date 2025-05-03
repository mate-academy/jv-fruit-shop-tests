package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy operationStrategy = new OperationStrategy(new BalanceHandler(),
            new PurchaseHandler());

    @Test
    void check_operationStrategy_OK() {
        FruitTransaction.Operation operationBalance = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation operationPurchase = FruitTransaction.Operation.PURCHASE;
        Assertions.assertEquals(operationBalance,operationStrategy.getHandler(operationBalance)
                .getType());
        Assertions.assertEquals(operationPurchase,operationStrategy.getHandler(operationPurchase)
                .getType());
    }

    @Test
    void check_operationStrategy_Exeption() {
        FruitTransaction.Operation operationReturn = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation operationSupply = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation operationBalance = FruitTransaction.Operation.BALANCE;

        Assertions.assertNull(operationStrategy.getHandler(operationReturn));
        Assertions.assertNull(operationStrategy.getHandler(operationSupply));
        Assertions.assertNotNull(operationStrategy.getHandler(operationBalance));

    }

}

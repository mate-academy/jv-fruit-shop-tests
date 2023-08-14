package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerImplTest {
    private BalanceOperationHandlerImpl balanceOperationHandler;

    @BeforeEach
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandlerImpl();
    }

    @Test
    public void handleOperation_Ok() {
        FruitTransaction balanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 120);
        balanceOperationHandler.handleOperation(balanceBanana);
        int quantity = FruitStorage.getQuantity("banana");
        Assertions.assertEquals(quantity, 120);
    }
}

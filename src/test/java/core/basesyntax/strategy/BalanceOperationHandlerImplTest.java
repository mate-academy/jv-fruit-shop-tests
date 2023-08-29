package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerImplTest {
    private static BalanceOperationHandlerImpl balanceOperationHandler;

    @BeforeAll
    public static void setUp() {
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

    @Test
    public void handleOperationWithNegativeValue_Ok() {
        FruitTransaction balanceBanana = new
                FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", -120);
        Assertions.assertThrows(RuntimeException.class, () ->
                balanceOperationHandler.handleOperation(balanceBanana));
    }
}

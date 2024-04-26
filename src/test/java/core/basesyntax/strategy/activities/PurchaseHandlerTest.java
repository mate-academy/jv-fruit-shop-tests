package core.basesyntax.strategy.activities;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String APPLE_FRUIT = "apple";
    private static final int APPLE_NEGATIVE_AMOUNT = -80;
    private static final FruitTransaction transaction = new FruitTransaction(
            FruitTransaction.Operation.PURCHASE,
            APPLE_FRUIT,
            APPLE_NEGATIVE_AMOUNT
    );

    @Test
    public void balanceNegative_Test() {
        assertThrows(RuntimeException.class, () ->
                new PurchaseHandler().executeTransaction(transaction));
    }
}

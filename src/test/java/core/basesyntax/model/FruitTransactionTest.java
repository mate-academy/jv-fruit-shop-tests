package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.RETURN;
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 10;
    private static final int INVALID_QUANTITY = -4;

    @Test
    void of_invalidOperationCode_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.of("k");
        });
    }

    @Test
    void setQuantity_invalidFruitQuantity_notOk() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(OPERATION, FRUIT_NAME, FRUIT_QUANTITY);
        assertThrows(RuntimeException.class, () -> fruitTransaction.setQuantity(INVALID_QUANTITY));
    }
}

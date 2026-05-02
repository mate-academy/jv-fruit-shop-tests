package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String FRUIT_APPLE = "apple";
    private static final int QUANTITY = 10;

    @Test
    void constructor_validInput_ok() {
        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE, QUANTITY);

        assertEquals(FruitTransaction.Operation.BALANCE, tx.getOperation());
        assertEquals(FRUIT_APPLE, tx.getFruit());
        assertEquals(QUANTITY, tx.getQuantity());
    }

    @Test
    void fromCode_validCode_returnsOperation() {
        FruitTransaction.Operation op = FruitTransaction.Operation.fromCode("s");
        assertEquals(FruitTransaction.Operation.SUPPLY, op);
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }
}

package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String GRAPE = "grape";

    @Test
    void constructor_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, BANANA, 15);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals(BANANA, transaction.getFruit());
        assertEquals(15, transaction.getQuantity());
    }

    @Test
    void constructor_nullOperation_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(null, APPLE, 10));
    }

    @Test
    void constructor_emptyFruit_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "", 5));
    }

    @Test
    void constructor_negativeQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(FruitTransaction.Operation.RETURN, ORANGE, -3));
    }

    @Test
    void constructor_zeroQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, GRAPE, 0);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals(GRAPE, transaction.getFruit());
        assertEquals(0, transaction.getQuantity());
    }

    @Test
    void fromCode_validCode_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void fromCode_nullCode_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode(null));
    }
}

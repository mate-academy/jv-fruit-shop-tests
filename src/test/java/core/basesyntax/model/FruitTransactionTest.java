package core.basesyntax.model;

import static core.basesyntax.constants.Constants.APPLE;
import static core.basesyntax.constants.Constants.BANANA;
import static core.basesyntax.constants.Constants.DIFFERENT_TEST_QUANTITY;
import static core.basesyntax.constants.Constants.NORMAL_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private final FruitTransaction fruitTransaction =
            new FruitTransaction(Operation.BALANCE, BANANA, NORMAL_QUANTITY);

    @Test
    void getOperation_correctOperation_ok() {
        assertEquals(Operation.BALANCE, fruitTransaction.getOperation());
    }

    @Test
    void getFruitName_correctFruitName_ok() {
        assertEquals(BANANA, fruitTransaction.getFruitName());
    }

    @Test
    void getQuantity_ok() {
        assertEquals(NORMAL_QUANTITY, fruitTransaction.getQuantity());
    }

    @Test
    void equals_sameObject_ok() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        assertEquals(transaction, transaction);
    }

    @Test
    void equals_null_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        assertNotEquals(null, transaction);
    }

    @Test
    void equals_differentClass_notOk() {
        FruitTransaction expectedTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        assertNotEquals(expectedTransaction, "SomeString");
    }

    @Test
    void equals_sameValues_ok() {
        FruitTransaction expectedTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        FruitTransaction currentTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        assertEquals(expectedTransaction, currentTransaction);
    }

    @Test
    void equals_differentOperation_notOk() {
        FruitTransaction expectedTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        FruitTransaction currentTransaction =
                new FruitTransaction(Operation.PURCHASE, APPLE, NORMAL_QUANTITY);
        assertNotEquals(expectedTransaction, currentTransaction);
    }

    @Test
    void equals_differentFruitName_notOk() {
        FruitTransaction expectedTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        FruitTransaction currentTransaction =
                new FruitTransaction(Operation.BALANCE, BANANA, NORMAL_QUANTITY);
        assertNotEquals(expectedTransaction, currentTransaction);
    }

    @Test
    void equals_differentQuantity_notOk() {
        FruitTransaction expectedTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        FruitTransaction currentTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, DIFFERENT_TEST_QUANTITY);
        assertNotEquals(expectedTransaction, currentTransaction);
    }

    @Test
    void hashCode_equalsObjects_sameHashCode_ok() {
        FruitTransaction expectedTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        FruitTransaction currentTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        assertEquals(expectedTransaction.hashCode(), currentTransaction.hashCode());
    }

    @Test
    void hashCode_notEqualsObjects_differentHashCode_notOk() {
        FruitTransaction expectedTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        FruitTransaction currentTransaction =
                new FruitTransaction(Operation.BALANCE, BANANA, NORMAL_QUANTITY);
        assertNotEquals(expectedTransaction.hashCode(), currentTransaction.hashCode());
    }

    @Test
    void hashCode_equalsContract_ok() {
        FruitTransaction expectedTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        FruitTransaction currentTransaction =
                new FruitTransaction(Operation.BALANCE, APPLE, NORMAL_QUANTITY);
        Set<FruitTransaction> set = new HashSet<>();
        set.add(expectedTransaction);
        assertTrue(set.contains(currentTransaction));
    }
}

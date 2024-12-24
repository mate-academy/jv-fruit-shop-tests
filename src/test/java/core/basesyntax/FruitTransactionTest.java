package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void constructor_validData_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        String fruit = "apple";
        int quantity = 100;

        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);

        assertEquals(operation, transaction.getOperation());
        assertEquals(fruit, transaction.getFruit());
        assertEquals(quantity, transaction.getQuantity());
    }

    @Test
    void fromCode_validCode_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
        assertEquals("Unknown operation code: x", exception.getMessage());
    }

    @Test
    void fromCode_nullCode_throwsException() {
        assertThrows(NullPointerException.class, () -> FruitTransaction.Operation.fromCode(null));
    }
}

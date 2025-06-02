package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.entity.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void gettersAndSetters_shouldWorkCorrectly() {
        FruitTransaction transaction = new FruitTransaction();

        transaction.setFruit("apple");
        transaction.setQuantity(25);
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);

        assertEquals("apple", transaction.getFruit());
        assertEquals(25, transaction.getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    void operationFromCode_shouldReturnCorrectOperation() {
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
    void operationFromCode_shouldThrowExceptionForInvalidCode() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
        assertTrue(ex.getMessage().contains("Unknown operation code"));
    }

    @Test
    void testGettersAndSetters() {
        FruitTransaction transaction = new FruitTransaction();

        transaction.setFruit("Apple");
        assertEquals("Apple", transaction.getFruit());

        transaction.setQuantity(10);
        assertEquals(10, transaction.getQuantity());

        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    void testOperationFromCode_ValidCodes() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void testOperationFromCode_InvalidCode() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode("x");
        });
        assertEquals("Unknown operation code: x", exception.getMessage());
    }

    @Test
    void testOperationGetSetCode() {
        FruitTransaction.Operation op = FruitTransaction.Operation.UNKNOWN;
        assertEquals("u", op.getOperation());

        op.setOperation("z");
        assertEquals("z", op.getOperation());
    }
}

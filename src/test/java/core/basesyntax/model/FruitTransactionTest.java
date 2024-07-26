package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void testConstructorAndGetters() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation(),
                "Operation should be BALANCE");
        assertEquals("apple", transaction.getFruit(),
                "Fruit should be apple");
        assertEquals(100, transaction.getQuantity(),
                "Quantity should be 100");
    }

    @Test
    void testSetOperation() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation(),
                "Operation should be SUPPLY");
    }

    @Test
    void testSetFruit() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
        transaction.setFruit("banana");

        assertEquals("banana", transaction.getFruit(),
                "Fruit should be banana");
    }

    @Test
    void testSetQuantity() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
        transaction.setQuantity(200);

        assertEquals(200, transaction.getQuantity(),
                "Quantity should be 200");
    }

    @Test
    void testToString() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation
                .BALANCE, "apple", 100);
        String expectedString = "FruitTransaction{operation=BALANCE, "
                + "fruit='apple', quantity=100}";

        assertEquals(expectedString, transaction.toString(),
                "toString output should match expected string");
    }

    @Test
    void testOperationFromCodeValid() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation
                .fromCode("b"), "Code 'b' should correspond to BALANCE operation");
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation
                .fromCode("s"), "Code 's' should correspond to SUPPLY operation");
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation
                .fromCode("p"), "Code 'p' should correspond to PURCHASE operation");
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation
                .fromCode("r"), "Code 'r' should correspond to RETURN operation");
    }

    @Test
    void testOperationFromCodeInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode("x");
        });

        String expectedMessage = "Unknown operation: x";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage),
                "Exception message should contain 'Unknown operation: x'");
    }
}

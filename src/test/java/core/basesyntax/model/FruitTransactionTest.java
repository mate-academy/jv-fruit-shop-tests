package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    public void testConstructorAndGetters_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation(),
                "Operation should be BALANCE");
        assertEquals("apple", transaction.getFruit(), "Fruit should be apple");
        assertEquals(100, transaction.getQuantity(), "Quantity should be 100");
    }

    @Test
    public void testSetters_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);

        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("banana");
        transaction.setQuantity(50);

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation(),
                "Operation should be SUPPLY");
        assertEquals("banana", transaction.getFruit(), "Fruit should be banana");
        assertEquals(50, transaction.getQuantity(), "Quantity should be 50");
    }

    @Test
    public void testOperationGetLetter_ok() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getLetter(),
                "BALANCE letter should be 'b'");
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getLetter(),
                "SUPPLY letter should be 's'");
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getLetter(),
                "PURCHASE letter should be 'p'");
        assertEquals("r", FruitTransaction.Operation.RETURN.getLetter(),
                "RETURN letter should be 'r'");
    }

    @Test
    public void testOperationGetOperation_validLetter_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getOperation("b"),
                "Should return BALANCE for 'b'");
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getOperation("s"),
                "Should return SUPPLY for 's'");
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getOperation("p"),
                "Should return PURCHASE for 'p'");
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getOperation("r"),
                "Should return RETURN for 'r'");
    }

    @Test
    public void testOperationGetOperation_invalidLetter_notOk() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getOperation("x"),
                "Expected exception for invalid operation letter"
        );

        assertTrue(exception.getMessage().contains("Operation not found by code: x"),
                "Exception message should mention missing operation for letter 'x'");
    }

    @Test
    public void testToString_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);

        String result = transaction.toString();

        String expected = "FruitTransaction{operation=PURCHASE, fruit='apple', quantity=50}";
        assertEquals(expected, result, "toString output should match expected format");
    }
}

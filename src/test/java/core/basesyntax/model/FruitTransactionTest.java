package core.basesyntax.model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void shouldReturnCorrectOperation_whenSetOperationIsCalled() {
        FruitTransaction fruitTransaction = new FruitTransaction(null, "", 0);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        assertEquals(fruitTransaction.getOperation(), FruitTransaction.Operation.BALANCE);

        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(fruitTransaction.getOperation(), FruitTransaction.Operation.SUPPLY);

    }

    @Test
    void shouldReturnCorrectFruit_whenSetFruitIsCalled() {
        FruitTransaction fruitTransaction = new FruitTransaction(null, "", 0);
        fruitTransaction.setFruit("banana");
        assertEquals(fruitTransaction.getFruit(), "banana");

        fruitTransaction.setFruit("apple");
        assertEquals("apple", fruitTransaction.getFruit());
    }

    @Test
    void shouldReturnCorrectQuantity_whenSetQuantityIsCalled() {
        FruitTransaction transaction = new FruitTransaction(null, "", 0);

        transaction.setQuantity(100);
        assertEquals(100, transaction.getQuantity());

        transaction.setQuantity(200);
        assertEquals(200, transaction.getQuantity());
    }

    @Test
    void shouldReturnCorrectOperation_whenValidCodeIsProvided() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getByCode("b"),
                "Code 'b' should return Operation.BALANCE");

        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getByCode("s"),
                "Code 's' should return Operation.SUPPLY");

        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getByCode("p"),
                "Code 'p' should return Operation.PURCHASE");

        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getByCode("r"),
                "Code 'r' should return Operation.RETURN");
    }

    @Test
    void shouldThrowException_whenInvalidOrNullCodeIsProvided() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getByCode("x");
        });
        assertEquals("Invalid operation code: x", exception.getMessage(),
                "Exception message should indicate an invalid code");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getByCode("");
        });
        assertEquals("Invalid operation code: ", exception.getMessage(),
                "Exception message should handle empty string properly");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getByCode(null);
        });
        assertEquals("Invalid operation code: null", exception.getMessage(),
                "Exception message should handle null value properly");
    }
}

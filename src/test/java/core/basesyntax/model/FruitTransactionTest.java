package core.basesyntax.model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void constructorAndGetters() {
        FruitTransaction actual = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        assertEquals(FruitTransaction.Operation.BALANCE, actual.getOperation());
        assertEquals("apple", actual.getFruit());
        assertEquals(10, actual.getQuantity());
    }

    @Test
    void setOperation_success() {
        FruitTransaction actual = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 50);

        actual.setOperation(FruitTransaction.Operation.BALANCE);
        actual.setFruit("apple");
        actual.setQuantity(10);

        assertEquals(FruitTransaction.Operation.BALANCE, actual.getOperation());
        assertEquals("apple", actual.getFruit());
        assertEquals(10, actual.getQuantity());
    }

    @Test
    void operationFromCode_validCode_shouldReturnOperation() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void operationFromCode_invalidCode_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));

        assertEquals("No operation with code: x", exception.getMessage());
    }
}

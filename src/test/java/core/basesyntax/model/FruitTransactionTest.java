package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void fruitTransaction_incorrectData_notOk() {
        assertThrows(RuntimeException.class,
                () -> new FruitTransaction(null,"apple", 1));
        assertThrows(RuntimeException.class,
                () -> new FruitTransaction("a","apple", 1));
    }

    @Test
    void fruitTransaction_correctData_Ok() {
        assertDoesNotThrow(() -> new FruitTransaction("b","apple", 1));
        assertDoesNotThrow(() -> new FruitTransaction("s","apple", 1));
        assertDoesNotThrow(() -> new FruitTransaction("p","apple", 1));
        assertDoesNotThrow(() -> new FruitTransaction("r","apple", 1));
    }

    @Test
    void fruitTransactionSettersGetters_correctData_Ok() {
        String expectedOperation = "p";
        String expectedFruit = "apple";
        int expectedQuantity = 1;
        FruitTransaction fruitTransaction =
                new FruitTransaction("b",null,0);
        fruitTransaction.setOperation(expectedOperation);
        fruitTransaction.setFruitName(expectedFruit);
        fruitTransaction.setQuantity(expectedQuantity);
        assertEquals(expectedOperation,fruitTransaction.getOperation().getCode());
        assertEquals(FruitTransaction.Operation.getByCode(expectedOperation),
                fruitTransaction.getOperation());
        assertEquals(expectedFruit,fruitTransaction.getFruitName());
        assertEquals(expectedQuantity,fruitTransaction.getQuantity());
    }

    @Test
    void toString_correctData_Ok() {
        String expectedOperation = "p";
        String expectedFruit = "apple";
        int expectedQuantity = 1;
        FruitTransaction fruitTransaction =
                new FruitTransaction(expectedOperation,expectedFruit, expectedQuantity);
        assertEquals("FruitTransaction{"
                + "operation=" + FruitTransaction.Operation.getByCode(expectedOperation)
                + ", fruit='" + expectedFruit + '\''
                + ", quantity=" + expectedQuantity
                + '}', fruitTransaction.toString());
    }
}

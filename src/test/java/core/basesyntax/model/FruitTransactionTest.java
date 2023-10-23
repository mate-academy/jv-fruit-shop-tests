package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String BANANA = "banana";

    @Test
    void add_ToQuantity_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, BANANA, 100);
        fruitTransaction.add(55);
        int expected = 155;
        int actual = fruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void subtract_CorrectQuantity_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.RETURN, BANANA, 50);
        fruitTransaction.subtract(30);
        int expected = 20;
        int actual = fruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void subtract_IncorrectQuantity_isNotOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.RETURN, BANANA, 20);
        assertThrows(RuntimeException.class,
                () -> fruitTransaction.subtract(30));
    }

    @Test
    void creating_FruitWithNegativeQuantity_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(Operation.RETURN, BANANA, -5));
    }

    @Test
    void set_FruitWithNegativeQuantity_isNotOk() {
        FruitTransaction fruitTransaction =
                FruitTransaction.of(Operation.RETURN, BANANA, 40);
        assertThrows(RuntimeException.class,
                () -> fruitTransaction.setQuantity(-20));
    }
}

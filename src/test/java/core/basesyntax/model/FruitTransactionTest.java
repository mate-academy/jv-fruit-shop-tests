package core.basesyntax.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FruitTransactionTest {
    private static final String BANANA = "banana";
    @Test
    void addToQuantity_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, BANANA, 100);
        fruitTransaction.add(55);
        int expected = 155;
        int actual = fruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void subtractCorrectQuantity_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.RETURN, BANANA, 50);
        fruitTransaction.subtract(30);
        int expected = 20;
        int actual = fruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void subtractIncorrectQuantity_isNotOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.RETURN, BANANA, 20);
        assertThrows(RuntimeException.class,
                () -> fruitTransaction.subtract(30));
    }

    @Test
    void creatingFruitWithNegativeQuantity_isNotOk() {
       assertThrows(RuntimeException.class,
               () -> FruitTransaction.of(Operation.RETURN, BANANA, -5));
    }

    @Test
    void setFruitWithNegativeQuantity_isNotOk() {
        FruitTransaction fruitTransaction =
                FruitTransaction.of(Operation.RETURN, BANANA, 40);
        assertThrows(RuntimeException.class,
                () -> fruitTransaction.setQuantity(-20));
    }
}
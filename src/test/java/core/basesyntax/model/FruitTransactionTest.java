package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    public void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void setOperation_Behavior_Ok() {
        fruitTransaction.setOperation("s");
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());

        fruitTransaction.setOperation("r");
        assertEquals(FruitTransaction.Operation.RETURN, fruitTransaction.getOperation());

        fruitTransaction.setOperation("b");
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());

        fruitTransaction.setOperation("p");
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation());
    }

    @Test
    public void setOperation_Behavior_NotOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fruitTransaction.setOperation("X")
        );
        assertEquals("operation don't exist for this code", exception.getMessage());
    }

    @Test
    public void setFruit_Behavior_Ok() {
        fruitTransaction.setFruit("apple");
        assertEquals(FruitTransaction.Fruits.APPLE.getCode(), fruitTransaction.getFruit());

        fruitTransaction.setFruit("banana");
        assertEquals(FruitTransaction.Fruits.BANANA.getCode(), fruitTransaction.getFruit());
    }

    @Test
    public void setFruit_Behavior_NotOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fruitTransaction.setFruit("orange")
        );
        assertEquals("such fruit don't exist", exception.getMessage());
    }
}

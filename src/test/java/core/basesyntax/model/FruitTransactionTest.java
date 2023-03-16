package core.basesyntax.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
    }

    @Test
    void setOperation_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
    }

    @Test
    void setFruit_ok() {
        fruitTransaction.setFruit("banana");
        assertEquals("banana", fruitTransaction.getFruit());
    }

    @Test
    void setQuantity_ok() {
        fruitTransaction.setQuantity(200);
        assertEquals(200, fruitTransaction.getQuantity());
    }

    @Test
    void getOperation_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());
    }

    @Test
    void getFruit_ok() {
        assertEquals("apple", fruitTransaction.getFruit());
    }

    @Test
    void getQuantity_ok() {
        assertEquals(100, fruitTransaction.getQuantity());
    }

    @Test
    void getOperation_validCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.getOperation("b");
        assertEquals(FruitTransaction.Operation.BALANCE, operation);
    }

    @Test
    void getOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getOperation("strawberry");
        });
    }
}
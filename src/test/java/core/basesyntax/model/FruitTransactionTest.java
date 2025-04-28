package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 12);
    }

    @Test
    void getOperation_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransaction.getOperation());
    }

    @Test
    void getOperation_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getOperation("d"));
    }

    @Test
    void setOperation_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(FruitTransaction.Operation.PURCHASE,
                fruitTransaction.getOperation());
    }

    @Test
    void getFruit_Ok() {
        assertEquals("apple", fruitTransaction.getFruit());
    }

    @Test
    void setFruit_Ok() {
        fruitTransaction.setFruit("banana");
        assertEquals("banana", fruitTransaction.getFruit());
    }

    @Test
    void getQuantity_Ok() {
        assertEquals(12,fruitTransaction.getQuantity());
    }

    @Test
    void setQuantity_Ok() {
        fruitTransaction.setQuantity(20);
        assertEquals(20,fruitTransaction.getQuantity());
    }

    @Test
    void testEquals_Ok() {
        FruitTransaction fruitTransactionExpected =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 12);
        assertEquals(fruitTransactionExpected, fruitTransaction);
    }

    @Test
    void testHashCode_Ok() {
        FruitTransaction fruitTransactionExpected =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 12);
        assertEquals(fruitTransactionExpected.hashCode(),
                fruitTransaction.hashCode());
    }

    @Test
    void getCode_Ok() {
        assertEquals("b", fruitTransaction.getOperation().getCode());
    }
}

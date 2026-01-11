package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple",
                10);
    }

    @Test
    void constructor_createValidTransaction_Ok() {
        assertEquals(FruitTransaction.Operation.SUPPLY,
                fruitTransaction.getOperation());
        assertEquals("apple", fruitTransaction.getFruit());
        assertEquals(10, fruitTransaction.getQuantity());
    }

    @Test
    void constructor_quantityIsNegative_NotOk() {
        assertThrows(RuntimeException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "apple",
                        -4));
    }

    @Test
    void setQuantity_validQuantity_Ok() {
        fruitTransaction.setQuantity(6);
        assertEquals(6, fruitTransaction.getQuantity());
    }

    @Test
    void setQuantity_quantityIsZero_Ok() {
        fruitTransaction.setQuantity(0);
        assertEquals(0, fruitTransaction.getQuantity());
    }

    @Test
    void setQuantity_quantityIsNegative_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fruitTransaction.setQuantity(-7));
    }

    @Test
    void setFruit_changeFruit_Ok() {
        fruitTransaction.setFruit("banana");
        assertEquals("banana", fruitTransaction.getFruit());
    }

    @Test
    void setOperation_changeOperation_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation());
    }

    //Operation ENUM
    @Test
    void fromCode_validCode_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.fromCode("r"));
        assertEquals(FruitTransaction.Operation.RETURN, fruitTransaction.getOperation());
    }

    @Test
    void fromCode_invalidCode_NotOk() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void fromCode_codeIsNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.fromCode(null));
    }

    @Test
    void getCode_returnCorrectCode_Ok() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode());
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getCode());
        assertEquals("r", FruitTransaction.Operation.RETURN.getCode());
    }
}

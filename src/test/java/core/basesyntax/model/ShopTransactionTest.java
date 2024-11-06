package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopTransactionTest {
    private ShopTransaction shopTransaction;

    @BeforeEach
    void setUp() {
        shopTransaction = new ShopTransaction(OperationType.RETURN, "apple", 15);
    }

    @Test
    void nullInstance_NotOk() {
        assertNotNull(shopTransaction);
    }

    @Test
    void weight_Ok() {
        int actual = shopTransaction.getWeight();
        assertEquals(15, actual);
    }

    @Test
    void fruitName_Ok() {
        String actual = shopTransaction.getFruitName();
        assertEquals("apple", actual);
    }

    @Test
    void operationType_Ok() {
        OperationType type = shopTransaction.getType();
        assertEquals(OperationType.RETURN, type);
    }
}

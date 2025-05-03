package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopTransactionTest {
    private ShopTransaction shopTransaction;

    @BeforeEach
    void setUp() {
        shopTransaction = new ShopTransaction(OperationType.RETURN, "apple", 15);
    }

    @Test
    void getWeight_isOk() {
        int actual = shopTransaction.getWeight();
        assertEquals(15, actual);
    }

    @Test
    void getFruitName_isOk() {
        String actual = shopTransaction.getFruitName();
        assertEquals("apple", actual);
    }

    @Test
    void getType_isOk() {
        OperationType type = shopTransaction.getType();
        assertEquals(OperationType.RETURN, type);
    }
}

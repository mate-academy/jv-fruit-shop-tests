package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ShopTransactionTest {
    @Test
    void constructor_AllValidArguments_ok() {
        assertDoesNotThrow(() -> new ShopTransaction(
                ShopTransaction.Operation.BALANCE,
                "banana",
                4));
    }

    @Test
    void constructor_productIsBlank_notOk() {
        assertThrows(RuntimeException.class, () ->
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, "  ", 5));
    }

    @Test
    void constructor_quantityIsNegativeNumber_notOk() {
        assertThrows(RuntimeException.class, () ->
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, "carrot", -55));
    }

    @Test
    void constructor_quantityIsZero_notOk() {
        assertThrows(RuntimeException.class, () ->
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, "carrot", 0));
    }

    @Test
    void operation_getCode_returnsCorrectCode() {
        assertEquals("b", ShopTransaction.Operation.BALANCE.getCode());
        assertEquals("s", ShopTransaction.Operation.SUPPLY.getCode());
        assertEquals("p", ShopTransaction.Operation.PURCHASE.getCode());
        assertEquals("r", ShopTransaction.Operation.RETURN.getCode());
    }

    @Test
    void hashCode_consistency_ok() {
        ShopTransaction t1 =
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, "banana", 10);
        ShopTransaction t2 =
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, "banana", 10);

        assertEquals(t1.hashCode(), t2.hashCode());
    }
}

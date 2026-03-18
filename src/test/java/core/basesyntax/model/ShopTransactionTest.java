package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ShopTransactionTest {
    private ShopTransaction shopTransaction;

    @Test
    void constructor_AllValidArguments_ok() {
        assertDoesNotThrow(() -> new ShopTransaction(
                ShopTransaction.Operation.BALANCE,
                "banana",
                4));
    }

    @Test
    void constructor_productIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, null, 2));
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
}

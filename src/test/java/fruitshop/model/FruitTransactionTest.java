package fruitshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction transaction = new FruitTransaction();

    @Test
    void setQuantity_negativeNumber_notOk() {
        assertThrows(IllegalArgumentException.class, () -> transaction.setQuantity(-10));
    }

    @Test
    void fromCode_validCodeB_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
    }

    @Test
    void fromCode_validCodeS_ok() {
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
    }

    @Test
    void fromCode_validCodeP_ok() {
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
    }

    @Test
    void fromCode_validCodeR_ok() {
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode_notOk() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x")
        );
        assertEquals("Unknown operation code: x", exception.getMessage());
    }

    @Test
    void fromCode_nullCode_notOk() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(null)
        );
        assertEquals("Unknown operation code: null", exception.getMessage());
    }
}

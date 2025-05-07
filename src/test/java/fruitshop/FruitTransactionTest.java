package fruitshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction transaction = new FruitTransaction();

    @Test
    void setOperation_validOperation_ok() {
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    void setFruit_validFruitName_ok() {
        transaction.setFruit("banana");
        assertEquals("banana", transaction.getFruit());
    }

    @Test
    void setQuantity_positiveNumber_ok() {
        transaction.setQuantity(42);
        assertEquals(42, transaction.getQuantity());
    }

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

    @Test
    void getCode_operationBalance_ok() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
    }

    @Test
    void getCode_operationSupply_ok() {
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode());
    }

    @Test
    void getCode_operationPurchase_ok() {
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getCode());
    }

    @Test
    void getCode_operationReturn_ok() {
        assertEquals("r", FruitTransaction.Operation.RETURN.getCode());
    }

    @Test
    void setOperation_null_notOk() {
        transaction.setOperation(null);
        assertNull(transaction.getOperation());
    }

    @Test
    void setFruit_null_ok() {
        transaction.setFruit(null);
        assertNull(transaction.getFruit());
    }
}

package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void createFruitTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                10
        );

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.operation());
        assertEquals("apple", transaction.fruit());
        assertEquals(10, transaction.quantity());
    }

    @Test
    void fromCode_OperationBalance_Ok() {
        assertEquals(
                FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"),
                "Test fromCode_OperationBalance_Ok failed"
        );

    }

    @Test
    void fromCode_OperationSupply_Ok() {
        assertEquals(
                FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"),
                "Test fromCode_OperationSupply_Ok failed"
        );
    }

    @Test
    void fromCode_OperationPurchase_Ok() {
        assertEquals(
                FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"),
                "Test fromCode_OperationPurchase_Ok failed"
        );
    }

    @Test
    void fromCode_OperationReturn_Ok() {
        assertEquals(
                FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"),
                "Test fromCode_OperationReturn_Ok failed"
        );
    }

    @Test
    void fromCode_OperationInvalid_NotOk() {
        assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("d")
        );
    }

    @Test
    void fromCode_OperationNUll_NotOk() {
        assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(null)
        );
    }
}

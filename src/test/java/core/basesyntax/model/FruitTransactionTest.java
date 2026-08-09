package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void getOperation_createdTransaction() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY,
                "banana",
                100
        );

        assertEquals(
                FruitTransaction.OperationType.SUPPLY,
                transaction.getOperation()
        );
    }

    @Test
    void getFruit_createdTransaction() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY,
                "banana",
                100
        );

        assertEquals("banana", transaction.getFruit());
    }

    @Test
    void getQuantity_createdTransaction() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY,
                "banana",
                100
        );

        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void getCode_operationType() {
        assertEquals(
                "b",
                FruitTransaction.OperationType.BALANCE.getCode()
        );

        assertEquals(
                "s",
                FruitTransaction.OperationType.SUPPLY.getCode()
        );

        assertEquals(
                "p",
                FruitTransaction.OperationType.PURCHASE.getCode()
        );

        assertEquals(
                "r",
                FruitTransaction.OperationType.RETURN.getCode()
        );
    }

    @Test
    void fromCode_balance() {
        assertEquals(
                FruitTransaction.OperationType.BALANCE,
                FruitTransaction.OperationType.fromCode("b")
        );
    }

    @Test
    void fromCode_supply() {
        assertEquals(
                FruitTransaction.OperationType.SUPPLY,
                FruitTransaction.OperationType.fromCode("s")
        );
    }

    @Test
    void fromCode_purchase() {
        assertEquals(
                FruitTransaction.OperationType.PURCHASE,
                FruitTransaction.OperationType.fromCode("p")
        );
    }

    @Test
    void fromCode_return() {
        assertEquals(
                FruitTransaction.OperationType.RETURN,
                FruitTransaction.OperationType.fromCode("r")
        );
    }

    @Test
    void fromCode_unknownCode() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.OperationType.fromCode("x")
        );

        assertEquals(
                "Unknown operation: x",
                exception.getMessage()
        );
    }

    @Test
    void fromCode_nullCode() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.OperationType.fromCode(null)
        );

        assertEquals(
                "Unknown operation: null",
                exception.getMessage()
        );
    }

    @Test
    void toString_createdTransaction() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY,
                "banana",
                100
        );

        assertEquals(
                "FruitTransaction{operation=SUPPLY, fruit='banana', quantity=100}",
                transaction.toString()
        );
    }
}

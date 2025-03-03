package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void setAndGetOperationBalance_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
    }

    @Test
    public void setAndGetOperationSupply_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    public void setAndGetOperationPurchase_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);

        assertEquals(FruitTransaction.Operation.PURCHASE, transaction.getOperation());
    }

    @Test
    public void setAndGetOperationReturn_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);

        assertEquals(FruitTransaction.Operation.RETURN, transaction.getOperation());
    }

    @Test
    public void setAndGetFruit_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");

        assertEquals("apple", transaction.getFruit().toLowerCase());
    }

    @Test
    public void setAndGetQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setQuantity(1000);

        assertEquals(1000, transaction.getQuantity());
    }

    @Test
    public void setAndGetInvalidQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transaction.setQuantity(-1000);
        });

        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    public void getOperationFromCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.getOperationFromCode("p");
        assertEquals(FruitTransaction.Operation.PURCHASE, operation);

        operation = FruitTransaction.Operation.getOperationFromCode("s");
        assertEquals(FruitTransaction.Operation.SUPPLY, operation);

        operation = FruitTransaction.Operation.getOperationFromCode("b");
        assertEquals(FruitTransaction.Operation.BALANCE, operation);

        operation = FruitTransaction.Operation.getOperationFromCode("r");
        assertEquals(FruitTransaction.Operation.RETURN, operation);
    }

    @Test
    public void getInvalidOperationFromCode_notOk() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getOperationFromCode("x");
        });

        assertEquals("Unknown operation: x", exception.getMessage());
    }
}

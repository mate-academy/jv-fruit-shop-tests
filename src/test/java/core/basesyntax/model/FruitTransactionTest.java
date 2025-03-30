package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void constructor_InitializesFieldsCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "Apple", 100);

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("Apple", transaction.getFruit());
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void setOperation_UpdatesOperationCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Banana", 50);
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);

        assertEquals(FruitTransaction.Operation.PURCHASE, transaction.getOperation());
    }

    @Test
    void setFruit_UpdatesFruitCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Orange", 30);
        transaction.setFruit("Mango");

        assertEquals("Mango", transaction.getFruit());
    }

    @Test
    void setQuantity_UpdatesQuantityCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Grapes", 20);
        transaction.setQuantity(45);

        assertEquals(45, transaction.getQuantity());
    }

    @Test
    void operationEnum_HasCorrectCodes() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode());
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getCode());
        assertEquals("r", FruitTransaction.Operation.RETURN.getCode());
    }
}

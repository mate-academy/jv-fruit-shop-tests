package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    public void setUp() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10);
    }

    @Test
    public void testSetOperation() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation(),
                "The operation should be updated to PURCHASE.");
    }

    @Test
    public void testSetFruit() {
        fruitTransaction.setFruit("banana");
        assertEquals("banana", fruitTransaction.getFruit(),
                "The fruit should be updated to banana.");
    }

    @Test
    public void testSetQuantity() {
        fruitTransaction.setQuantity(20);
        assertEquals(20, fruitTransaction.getQuantity(),
                "The quantity should be updated to 20.");
    }

    @Test
    public void testGetCode() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode(),
                "The code for the BALANCE operation should be 'b'.");
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode(),
                "The code for the SUPPLY operation should be 's'.");
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getCode(),
                "The code for the PURCHASE operation should be 'p'.");
        assertEquals("r", FruitTransaction.Operation.RETURN.getCode(),
                "The code for the RETURN operation should be 'r'.");
    }

    @Test
    void constructor_InitializesFieldsCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 100);

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void setOperation_UpdatesOperationCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 50);
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);

        assertEquals(FruitTransaction.Operation.PURCHASE, transaction.getOperation());
    }

    @Test
    void setFruit_UpdatesFruitCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "orange", 30);
        transaction.setFruit("mango");

        assertEquals("mango", transaction.getFruit());
    }

    @Test
    void setQuantity_UpdatesQuantityCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "grapes", 20);
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

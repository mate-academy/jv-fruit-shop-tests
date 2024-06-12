package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final int APPLE_QUANTITY_DEFAULT = 10;

    @Test
    public void test_setTransaction() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setTransaction(FruitTransaction.Operation.BALANCE, "apple",
                APPLE_QUANTITY_DEFAULT);

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation(),
                "There is problem with getOperation() method");
        assertEquals("apple", transaction.getFruit(),
                "There is problem with getFruit() method");
        assertEquals(APPLE_QUANTITY_DEFAULT, transaction.getQuantity(),
                "There is problem with getQuantity() method");
    }

    @Test
    public void test_operationBalance_validCode() {
        FruitTransaction.Operation operationOne = FruitTransaction.Operation.fromCode("b");
        assertEquals(FruitTransaction.Operation.BALANCE, operationOne,
                "Operation should be 'BALANCE'");
    }

    @Test
    public void test_operationSupply_validCode() {
        FruitTransaction.Operation operationTwo = FruitTransaction.Operation.fromCode("s");
        assertEquals(FruitTransaction.Operation.SUPPLY, operationTwo,
                "Operation should be 'SUPPLY'");
    }

    @Test
    public void test_operationPurchase_validCode() {
        FruitTransaction.Operation operationThree = FruitTransaction.Operation.fromCode("p");
        assertEquals(FruitTransaction.Operation.PURCHASE, operationThree,
                "Operation should be 'PURCHASE'");
    }

    @Test
    public void test_operationReturn_validCode() {
        FruitTransaction.Operation operationFour = FruitTransaction.Operation.fromCode("r");
        assertEquals(FruitTransaction.Operation.RETURN, operationFour,
                "Operation should be 'RETURN'");
    }

    @Test
    public void test_operationFromCode_invalidCode_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                        FruitTransaction.Operation.fromCode("x"),
                "Should throw IllegalArgumentException for invalid code");
    }
}

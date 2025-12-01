package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void constructor_validData_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",100);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void getOperation_returnsCorrectValue_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",50);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    void getFruit_returnsCorrectValue_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,"orange",25);
        assertEquals("orange", transaction.getFruit());
    }

    @Test
    void getQuantity_returnsCorrectValue_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,"apple",75);
        assertEquals(75, transaction.getQuantity());
    }

    @Test
    void operation_fromCode_balanceCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode("b");
        assertEquals(FruitTransaction.Operation.BALANCE, operation);
    }

    @Test
    void operation_fromCode_supplyCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode("s");
        assertEquals(FruitTransaction.Operation.SUPPLY, operation);
    }

    @Test
    void operation_fromCode_purchaseCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode("p");
        assertEquals(FruitTransaction.Operation.PURCHASE, operation);
    }

    @Test
    void operation_fromCode_returnCode_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode("r");
        assertEquals(FruitTransaction.Operation.RETURN, operation);
    }

    @Test
    void operation_fromCode_caseInsensitive_ok() {
        FruitTransaction.Operation operationLower = FruitTransaction.Operation.fromCode("b");
        FruitTransaction.Operation operationUpper = FruitTransaction.Operation.fromCode("B");
        assertEquals(FruitTransaction.Operation.BALANCE, operationLower);
        assertEquals(FruitTransaction.Operation.BALANCE, operationUpper);
    }

    @Test
    void operation_fromCode_invalidCode_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void operation_fromCode_nullCode_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(null));
    }

    @Test
    void operation_fromCode_emptyCode_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(""));
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void operation_values_containsAllOperations() {
        FruitTransaction.Operation[] operations = FruitTransaction.Operation.values();
        assertEquals(4, operations.length);
        assertTrue(contains(operations, FruitTransaction.Operation.BALANCE));
        assertTrue(contains(operations, FruitTransaction.Operation.SUPPLY));
        assertTrue(contains(operations, FruitTransaction.Operation.PURCHASE));
        assertTrue(contains(operations, FruitTransaction.Operation.RETURN));
    }

    @Test
    void operation_valueOf_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.valueOf("BALANCE");
        assertEquals(FruitTransaction.Operation.BALANCE, operation);
    }

    private boolean contains(FruitTransaction.Operation[] operations,
                             FruitTransaction.Operation target) {
        for (FruitTransaction.Operation operation : operations) {
            if (operation == target) {
                return true;
            }
        }
        return false;
    }
}

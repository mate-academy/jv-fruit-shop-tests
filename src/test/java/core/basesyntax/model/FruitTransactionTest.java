package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.Operation.convertor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void convert_b_ToEnumBalance_shouldReturnBalance() {
        String typeOfBalance = "b";
        var actual = convertor(typeOfBalance);
        var expected = FruitTransaction.Operation.BALANCE;
        assertEquals(expected, actual, "Expected BALANCE operation for 'b'");
    }

    @Test
    void convert_s_ToEnumSupply_shouldReturnSupply() {
        String typeOfBalance = "s";
        var actual = convertor(typeOfBalance);
        var expected = FruitTransaction.Operation.SUPPLY;
        assertEquals(expected, actual, "Expected SUPPLY operation for 's'");
    }

    @Test
    void convert_p_ToEnumPurchase_shouldReturnPurchase() {
        String typeOfBalance = "p";
        var actual = convertor(typeOfBalance);
        var expected = FruitTransaction.Operation.PURCHASE;
        assertEquals(expected, actual, "Expected PURCHASE operation for 'p'");
    }

    @Test
    void convert_r_ToEnumReturn_shouldReturnReturn() {
        String typeOfBalance = "r";
        var actual = convertor(typeOfBalance);
        var expected = FruitTransaction.Operation.RETURN;
        assertEquals(expected, actual, "Expected RETURN operation for 'r'");
    }

    @Test
    void convert_operationDoesntFound_throwRuntimeException() {
        String message = "k";
        Exception exception = assertThrows(RuntimeException.class, () ->
                convertor(message));

        String actual = exception.getMessage();
        String expected = "Operation with name " + message + " not found.";
        assertEquals(expected, actual);
    }

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        Assertions.assertEquals("banana", transaction.getFruit());
        Assertions.assertEquals(100, transaction.getQuantity());
    }

    @Test
    void setQuantity_ShouldUpdateQuantity() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50);
        transaction.setQuantity(75);
        Assertions.assertEquals(75, transaction.getQuantity());
    }

    @Test
    void equals_ShouldReturnTrueForEqualObjects() {
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 30);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 30);
        Assertions.assertEquals(transaction1, transaction2);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentObjects() {
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "grape", 40);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "grape", 20);
        Assertions.assertNotEquals(transaction1, transaction2);
    }

    @Test
    void hashCode_ShouldBeConsistentWithEquals() {
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "kiwi", 15);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "kiwi", 15);
        Assertions.assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    void convertor_ShouldThrowExceptionForInvalidString() {
        RuntimeException exception = Assertions.assertThrows(
                RuntimeException.class,
                () -> FruitTransaction.Operation.convertor("x")
        );
        Assertions.assertEquals("Operation with name x not found.", exception.getMessage());
    }

    @Test
    void getTypeOfOperation_ShouldReturnCorrectString() {
        Assertions.assertEquals("b", FruitTransaction.Operation.BALANCE.getTypeOfOperation());
        Assertions.assertEquals("s", FruitTransaction.Operation.SUPPLY.getTypeOfOperation());
    }
}



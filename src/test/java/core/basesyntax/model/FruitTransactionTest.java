package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.Operation.convertor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void convert_balanceOperationCode_shouldReturnBalance() {
        String typeOfBalance = "b";
        var actual = convertor(typeOfBalance);
        var expected = FruitTransaction.Operation.BALANCE;
        assertEquals(expected, actual, "Expected BALANCE operation for 'b'");
    }

    @Test
    void convert_supplyOperationCOde_shouldReturnSupply() {
        String typeOfBalance = "s";
        var actual = convertor(typeOfBalance);
        var expected = FruitTransaction.Operation.SUPPLY;
        assertEquals(expected, actual, "Expected SUPPLY operation for 's'");
    }

    @Test
    void convert_purchaseOperationCode_shouldReturnPurchase() {
        String typeOfBalance = "p";
        var actual = convertor(typeOfBalance);
        var expected = FruitTransaction.Operation.PURCHASE;
        assertEquals(expected, actual, "Expected PURCHASE operation for 'p'");
    }

    @Test
    void convert_returnOperationCOde_shouldReturnFruit() {
        String typeOfBalance = "r";
        var actual = convertor(typeOfBalance);
        var expected = FruitTransaction.Operation.RETURN;
        assertEquals(expected, actual, "Expected RETURN operation for 'r'");
    }

    @Test
    void convert_operationDoesntFound_throwRuntimeException() {
        String message = "k";
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                convertor(message));

        String actual = exception.getMessage();
        String expected = "Operation with name " + message + " not found.";
        assertEquals(expected, actual);
    }
}

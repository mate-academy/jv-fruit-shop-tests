package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String EXCEPTION_MESSAGE = "Not valid code!";

    @Test
    void findOperationByCode_validCode_Ok() {
        String code = "b";
        FruitTransaction.Operation operation = FruitTransaction.Operation.findOperationByCode(code);
        assertEquals(FruitTransaction.Operation.BALANCE, operation);
    }

    @Test
    void findOperationByCode_invalidCode_NotOk() {
        String invalidCode = "x";
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.findOperationByCode(invalidCode),EXCEPTION_MESSAGE);
    }

    @Test
    void findOperationByCode_nullCode_NotOk() {
        String nullCode = null;
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.findOperationByCode(nullCode),EXCEPTION_MESSAGE);
    }

    @Test
    void findOperationByCode_emptyCode_NotOk() {
        String emptyCode = "";
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.findOperationByCode(emptyCode),EXCEPTION_MESSAGE);
    }
}

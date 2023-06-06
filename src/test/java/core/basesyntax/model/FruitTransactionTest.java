package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String BALANCE_OPERATION_CODE = "b";
    private static final String SUPPLY_OPERATION_CODE = "s";
    private static final String PURCHASE_OPERATION_CODE = "p";
    private static final String RETURN_OPERATION_CODE = "r";
    private static final String EMPTY_STRING = "";
    private static final String WRONG_CODE = "wrong-code";

    @DisplayName("Checking for passing null as code value")
    @Test
    void getByCode_nullCode_notOk() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.getByCode(null));
    }

    @DisplayName("Checking for passing empty string as a code value")
    @Test
    void getByCode_emptyString_notOk() {
        assertThrows(RuntimeException.class, () ->
                FruitTransaction.Operation.getByCode(EMPTY_STRING));
    }

    @DisplayName("Checking for passing wrong code as code value")
    @Test
    void getByCode_wrongCode_notOk() {
        assertThrows(RuntimeException.class, () ->
                FruitTransaction.Operation.getByCode(WRONG_CODE));
    }

    @DisplayName("Checking for passing balance operation code as code value")
    @Test
    void getByCode_balanceOperationCode_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual =
                FruitTransaction.Operation.getByCode(BALANCE_OPERATION_CODE);
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing supply operation code as code value")
    @Test
    void getByCode_supplyOperationCode_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual =
                FruitTransaction.Operation.getByCode(SUPPLY_OPERATION_CODE);
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing purchase operation code as code value")
    @Test
    void getByCode_purchaseOperationCode_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual =
                FruitTransaction.Operation.getByCode(PURCHASE_OPERATION_CODE);
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing return operation code as code value")
    @Test
    void getByCode_returnOperationCode_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual =
                FruitTransaction.Operation.getByCode(RETURN_OPERATION_CODE);
        assertEquals(expected, actual);
    }
}

package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    private static final String OPERATION_BALANCE = "b";
    private static final String OPERATION_SUPPLY = "s";
    private static final String OPERATION_PURCHASE = "p";
    private static final String OPERATION_RETURN = "r";
    private static final String EMPTY_STRING = "";

    @Test
    void getOperationByCode_givenBalance_OK() {
        Operation actual = Operation.getOperationByCode(OPERATION_BALANCE);
        assertEquals(Operation.BALANCE, actual);
    }

    @Test
    void getOperationByCode_givenSupply_OK() {
        Operation actual = Operation.getOperationByCode(OPERATION_SUPPLY);
        assertEquals(Operation.SUPPLY, actual);
    }

    @Test
    void getOperationByCode_givenPurchase_OK() {
        Operation actual = Operation.getOperationByCode(OPERATION_PURCHASE);
        assertEquals(Operation.PURCHASE, actual);
    }

    @Test
    void getOperationByCode_givenReturn_OK() {
        Operation actual = Operation.getOperationByCode(OPERATION_RETURN);
        assertEquals(Operation.RETURN, actual);
    }

    @Test
    void getOperationByCode_givenInvalidCode_NotOk() {
        String invalidCode = "invalid";

        assertThrows(IllegalArgumentException.class, () -> {
            Operation.getOperationByCode(invalidCode);
        });
    }

    @Test
    void getOperationByCode_givenNull_NotOk() {
        String nullString = null;

        assertThrows(IllegalArgumentException.class, () -> {
            Operation.getOperationByCode(nullString);
        });
    }

    @Test
    void getOperationByCode_givenEmptyString_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            Operation.getOperationByCode(EMPTY_STRING);
        });
    }
}

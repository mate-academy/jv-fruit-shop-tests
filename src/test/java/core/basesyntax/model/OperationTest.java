package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationTest {
    private static Map<Operation, String> operation;

    @BeforeEach
    public void setUp() {
        operation = Map.of(
                Operation.BALANCE, "b",
                Operation.SUPPLY, "s",
                Operation.PURCHASE, "p",
                Operation.RETURN, "r");
    }

    @Test
    void getOperationByCode_givenBalance_OK() {
        String inputCode = operation.get(Operation.BALANCE);

        Operation actual = Operation.getOperationByCode(inputCode);
        assertEquals(Operation.BALANCE, actual);
    }

    @Test
    void getOperationByCode_givenSupply_OK() {
        String inputCode = operation.get(Operation.SUPPLY);

        Operation actual = Operation.getOperationByCode(inputCode);
        assertEquals(Operation.SUPPLY, actual);
    }

    @Test
    void getOperationByCode_givenPurchase_OK() {
        String inputCode = operation.get(Operation.PURCHASE);

        Operation actual = Operation.getOperationByCode(inputCode);
        assertEquals(Operation.PURCHASE, actual);
    }

    @Test
    void getOperationByCode_givenReturn_OK() {
        String inputCode = operation.get(Operation.RETURN);

        Operation actual = Operation.getOperationByCode(inputCode);
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
        String emptyString = "";

        assertThrows(IllegalArgumentException.class, () -> {
            Operation.getOperationByCode(emptyString);
        });
    }
}

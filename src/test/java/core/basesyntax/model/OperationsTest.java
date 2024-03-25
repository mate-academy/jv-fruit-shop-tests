package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OperationsTest {

    @Test
    void getOperationByCodeValidCodes_ok() {
        assertAll("Valid Operations Codes",
                () -> assertEquals(Operations.BALANCE, Operations.getOperationByCode("b")),
                () -> assertEquals(Operations.SUPPLY, Operations.getOperationByCode("s")),
                () -> assertEquals(Operations.PURCHASE, Operations.getOperationByCode("p")),
                () -> assertEquals(Operations.RETURN, Operations.getOperationByCode("r"))
        );
    }

    @Test
    void getOperationByCodeInvalidCode_notOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Operations.getOperationByCode("x"));
        assertTrue(exception.getMessage().contains("Invalid code: x"));
    }
}

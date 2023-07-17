package core.basesyntax.model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void getByCode_wrongOperationCode_NotOk() {
        String code = "wrong code";
        assertThrows(IllegalArgumentException.class, () -> Operation.getByCode(code));
    }

    @Test
    void getByCode_nullOperationCode_NotOk() {
        String code = null;
        try {
            Operation.getByCode(code);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("If operation code is null ErrorDataException should be thrown");
    }
}


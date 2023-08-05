package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void getOperationByCode_ValidCode_ok() {
        String validCode = "r";
        Operation result = Operation.getOperationByCode(validCode);
        assertEquals(Operation.RETURN, result);
    }

    @Test
    void getOperationByCode_InvalidCode_notOk() {
        String invalidCode = "f";
        assertThrows(IllegalArgumentException.class,
                () -> Operation.getOperationByCode(invalidCode));
    }

    @Test
    void getOperationByCode_EmptyCode_notOk() {
        String invalidCode = "";
        assertThrows(IllegalArgumentException.class,
                () -> Operation.getOperationByCode(invalidCode));
    }

    @Test
    void getOperationByCode_WhiteSpaceCode_notOk() {
        String invalidCode = " ";
        assertThrows(IllegalArgumentException.class,
                () -> Operation.getOperationByCode(invalidCode));
    }
}

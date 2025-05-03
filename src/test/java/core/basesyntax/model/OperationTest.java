package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    private static final String VALID_CODE = "r";
    private static final String INVALID_CODE = "f";
    private static final String EMPTY_CODE = "";
    private static final String WHITE_SPACE_CODE = " ";

    @Test
    void getOperationByCode_validCode_ok() {
        Operation result = Operation.getOperationByCode(VALID_CODE);
        assertEquals(Operation.RETURN, result);
    }

    @Test
    void getOperationByCode_invalidCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Operation.getOperationByCode(INVALID_CODE));
    }

    @Test
    void getOperationByCode_emptyCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Operation.getOperationByCode(EMPTY_CODE));
    }

    @Test
    void getOperationByCode_whiteSpaceCode_notOk() {

        assertThrows(IllegalArgumentException.class,
                () -> Operation.getOperationByCode(WHITE_SPACE_CODE));
    }
}

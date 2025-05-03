package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void getByCode_validCode_Ok() {
        Operation actual1 = Operation.getByCode("b");
        Operation expected1 = Operation.BALANCE;
        assertEquals(expected1, actual1);

        Operation actual2 = Operation.getByCode("s");
        Operation expected2 = Operation.SUPPLY;
        assertEquals(expected2, actual2);

        Operation actual3 = Operation.getByCode("p");
        Operation expected3 = Operation.PURCHASE;
        assertEquals(expected3, actual3);

        Operation actual4 = Operation.getByCode("r");
        Operation expected4 = Operation.RETURN;
        assertEquals(expected4, actual4);
    }

    @Test
    void getByCode_nullCode_notOk() {
        assertThrows(IllegalArgumentException.class, () -> Operation.getByCode(null),
                "Null code should throw IllegalArgumentException");
    }

    @Test
    void getByCode_invalidCode_notOk() {
        assertThrows(IllegalArgumentException.class, () -> Operation.getByCode("a"),
                "Invalid code should throw IllegalArgumentException");
    }

    @Test
    void getByCode_emptyCode_notOk() {
        assertThrows(IllegalArgumentException.class, () -> Operation.getByCode(""),
                "Empty code should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> Operation.getByCode(" "),
                "Empty code should throw IllegalArgumentException");
    }
}

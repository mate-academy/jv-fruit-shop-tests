package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OperationsTest {
    private static final String BALANCE_OPERATION = "b";
    private static final String UNKNOWN_OPERATION = "k";
    private static final String ENUM_VALUE_IS_PRESENT = "r";
    private static final String ENUM_VALUE_NOT_PRESENT = "d";

    @Test
    public void containsTrueTest_Ok() {
        assertTrue(Operations.contains(BALANCE_OPERATION));
    }

    @Test
    public void containsFalseTest_Ok() {
        assertFalse(Operations.contains(UNKNOWN_OPERATION));
    }

    @Test
    public void getEnumByValueTest_Ok() {
        Operations expected = Operations.RETURN;
        Operations actual = Operations.getEnumByString(ENUM_VALUE_IS_PRESENT);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getEnumByValueTest_NotOk() {
        Operations.getEnumByString(ENUM_VALUE_NOT_PRESENT);
    }
}

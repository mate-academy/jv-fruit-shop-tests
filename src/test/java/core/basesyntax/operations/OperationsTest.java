package core.basesyntax.operations;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class OperationsTest {
    private static final String BOOLEAN_TRUE = "b";
    private static final String BOOLEAN_FALSE = "k";
    private static final String ENUM_VALUE_IS_PRESENT = "r";
    private static final String ENUM_VALUE_NOT_PRESENT = "d";

    @Test
    public void containsTrueTest_Ok() {
        assertTrue(Operations.contains(BOOLEAN_TRUE));
    }

    @Test
    public void containsFalseTest_Ok() {
        assertFalse(Operations.contains(BOOLEAN_FALSE));
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
package core.basesyntax.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OperationsTest {
    private static final String CORRECT_TYPE = "P";
    private static final String WRONG_TYPE = "Z";

    @Test
    public void containsTestWithCorrectInput_Ok() {
        assertTrue(Operations.contains(CORRECT_TYPE));
    }

    @Test
    public void containsTestWithWrongInput_NotOk() {
        assertFalse(Operations.contains(WRONG_TYPE));
    }
}

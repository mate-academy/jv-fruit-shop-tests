package core.basesyntax.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OperationTypeTest {
    private static final String balance = "b";
    private static final String nonExistentOperation = "k";

    @Test
    public void isPresentTest_OK() {
        assertTrue(OperationType.isPresent(balance));
        assertFalse(OperationType.isPresent(nonExistentOperation));
    }
}

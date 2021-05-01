package core.basesyntax.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class OperationTypeTest {
    private static final String balance = "b";
    private static final String nonExistentOperation = "k";

    @Test
    public void isPresentTest_OK() {
        assertTrue(OperationType.isPresent(balance));
        assertFalse(OperationType.isPresent(nonExistentOperation));
    }
}
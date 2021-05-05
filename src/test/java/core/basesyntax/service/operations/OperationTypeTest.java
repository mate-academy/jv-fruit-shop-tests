package core.basesyntax.service.operations;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTypeTest {
    private static OperationType operationType;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationType = OperationType.b;
    }

    @Test
    public void operationType_Ok() {
        assertNotNull(operationType);
    }
}

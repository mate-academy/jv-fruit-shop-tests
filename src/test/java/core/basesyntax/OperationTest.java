package core.basesyntax;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationTest {
    @Test
    public void operationTest_Ok() {
        Operation actual = Operation.getOperationByCode("b");
        String expected = "BALANCE";
        assertEquals(expected, actual.name());
    }

    @Test(expected = RuntimeException.class)
    public void operationTest_WrongCode_NotOk() {
        Operation.getOperationByCode("x");
    }
}

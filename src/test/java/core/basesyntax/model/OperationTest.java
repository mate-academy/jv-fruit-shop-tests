package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationTest {

    @Test
    public void getOperationByFirstLetter_ValidOperationLetter_isOk() {
        Operation actual = Operation.getOperationByFirstLetter("b");
        Operation expected = Operation.BALANCE;
        assertEquals(expected, actual);
    }
}

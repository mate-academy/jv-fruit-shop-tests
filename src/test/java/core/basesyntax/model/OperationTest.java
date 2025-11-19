package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

class OperationTest {

    @Test
    public void validSymbolCheck() {
        assertEquals(Operation.BALANCE, Operation.parseOperation("b", 1));
        assertEquals(Operation.SUPPLY, Operation.parseOperation("s", 1));
        assertEquals(Operation.RETURN, Operation.parseOperation("r", 1));
        assertEquals(Operation.PURCHASE, Operation.parseOperation("p", 1));
    }
}

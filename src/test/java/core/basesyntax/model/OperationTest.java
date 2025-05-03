package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void convert_UnexpectedCode_To_Operation_NotOk() {
        assertThrows(RuntimeException.class,
                () -> Operation.convertToOperation("invalid"));
    }

    @Test
    void convert_ValidCode_Ok() {
        Operation balance = Operation.BALANCE;
        assertEquals(balance, Operation.convertToOperation("b"));
        Operation purchase = Operation.PURCHASE;
        assertEquals(purchase, Operation.convertToOperation("p"));
        Operation returnOp = Operation.RETURN;
        assertEquals(returnOp, Operation.convertToOperation("r"));
        Operation supply = Operation.SUPPLY;
        assertEquals(supply, Operation.convertToOperation("s"));
    }
}

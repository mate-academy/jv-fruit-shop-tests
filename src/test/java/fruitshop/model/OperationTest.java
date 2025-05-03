package fruitshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void getOperation_validCase_ok() {
        String operation = "p";
        Operation expected = Operation.PURCHASE;
        Operation actual = Operation.getOperation(operation);
        assertEquals(expected, actual);
    }

    @Test
    void getOperation_operationDoesntExist_notOk() {
        String operation = "t";
        assertThrows(IllegalArgumentException.class, () -> Operation.getOperation(operation));
    }
}

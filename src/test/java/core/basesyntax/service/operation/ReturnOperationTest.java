package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    @Test
    @DisplayName("Return operation test")
    void returnOperationGetQuantity_ok() {
        ReturnOperation returnOperation = new ReturnOperation();
        int expectedReturnOperationResult = 10;
        int actualReturnOperationResult = returnOperation.getQuantity(5, 5);
        assertEquals(expectedReturnOperationResult, actualReturnOperationResult);
    }

    @Test
    @DisplayName("Return operation invalid test")
    void returnOperationGetWrongQuantity_notOk() {
        ReturnOperation returnOperation = new ReturnOperation();
        int expectedReturnOperationResult = 9;
        int actualReturnOperationResult = returnOperation.getQuantity(5, 5);
        assertNotEquals(expectedReturnOperationResult, actualReturnOperationResult);
    }
}

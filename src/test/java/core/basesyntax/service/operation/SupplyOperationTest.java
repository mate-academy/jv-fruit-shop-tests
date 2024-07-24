package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    @Test
    @DisplayName("Supply operation test")
    void supplyOperationGetQuantity_ok() {
        SupplyOperation supplyOperation = new SupplyOperation();
        int expectedSupplyResult = 10;
        int actualSupplyResult = supplyOperation.getQuantity(5, 5);
        assertEquals(expectedSupplyResult, actualSupplyResult);
    }

    @Test
    @DisplayName("Supply operation invalid test")
    void supplyOperationGetWrongQuantity_notOk() {
        SupplyOperation supplyOperation = new SupplyOperation();
        int expectedSupplyResult = 9;
        int actualSupplyResult = supplyOperation.getQuantity(5, 5);
        assertNotEquals(expectedSupplyResult, actualSupplyResult);
    }
}

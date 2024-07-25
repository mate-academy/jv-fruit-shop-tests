package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    @Test
    @DisplayName("Balance operation test")
    void balanceOperationGetQuantity_ok() {
        BalanceOperation balanceOperation = new BalanceOperation();
        int expectedBalanceResult = 10;
        int actualBalanceResult = balanceOperation.getQuantity(5, 5);
        assertEquals(expectedBalanceResult, actualBalanceResult);
    }

    @Test
    @DisplayName("Balance operation invalid test")
    void balanceOperationGetWrongQuantity_notOk() {
        BalanceOperation balanceOperation = new BalanceOperation();
        int expectedWrongBalanceResult = 10;
        int actualBalanceResult = balanceOperation.getQuantity(5, 4);
        assertNotEquals(expectedWrongBalanceResult, actualBalanceResult);
    }
}

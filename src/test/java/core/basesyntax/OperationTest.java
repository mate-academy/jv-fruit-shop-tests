package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntex.model.Operation;
import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void fromCode_validCodeBalance_ok() {
        Operation result = Operation.fromCode("b");
        assertEquals(Operation.BALANCE, result);
    }

    @Test
    void fromCode_validCodeSupply_ok() {
        Operation result = Operation.fromCode("s");
        assertEquals(Operation.SUPPLY, result);
    }

    @Test
    void fromCode_validCodePurchase_ok() {
        Operation result = Operation.fromCode("p");
        assertEquals(Operation.PURCHASE, result);
    }

    @Test
    void fromCode_validCodeReturn_ok() {
        Operation result = Operation.fromCode("r");
        assertEquals(Operation.RETURN, result);
    }

    @Test
    void fromCode_invalidCode_notOk() {
        String invalidCode = "x";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Operation.fromCode(invalidCode);
        });
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void getCode_balance_ok() {
        assertEquals("b", Operation.BALANCE.getCode());
    }

    @Test
    void getCode_supply_ok() {
        assertEquals("s", Operation.SUPPLY.getCode());
    }

    @Test
    void getCode_purchase_ok() {
        assertEquals("p", Operation.PURCHASE.getCode());
    }

    @Test
    void getCode_return_ok() {
        assertEquals("r", Operation.RETURN.getCode());
    }
}

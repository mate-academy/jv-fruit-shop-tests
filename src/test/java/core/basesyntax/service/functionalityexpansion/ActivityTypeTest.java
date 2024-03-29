package core.basesyntax.service.functionalityexpansion;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ActivityTypeTest {
    private static final String PURCHASE_STRING = "p";
    private static final String RETURN_STRING = "r";
    private static final String SUPPLY_STRING = "s";
    private static final String BALANCE_STRING = "b";
    private static final String INVALID_CODE = "cx";
    private String code;

    @Test
    void getByCode_incorrectInput_NotOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ActivityType.getByCode(INVALID_CODE));
        assertEquals("Unknown activity code: " + INVALID_CODE, exception.getMessage(),
                "Incorrect validation error message");
    }

    @Test
    void getByCode_activityPurchase_Ok() {
        code = PURCHASE_STRING;
        assertEquals(ActivityType.PURCHASE, ActivityType.getByCode(code));
    }

    @Test
    void getByCode_activityBalance_Ok() {
        code = BALANCE_STRING;
        assertEquals(ActivityType.BALANCE, ActivityType.getByCode(code));
    }

    @Test
    void getByCode_activitySupply_Ok() {
        code = SUPPLY_STRING;
        assertEquals(ActivityType.SUPPLY, ActivityType.getByCode(code));
    }

    @Test
    void getByCode_activityReturn_Ok() {
        code = RETURN_STRING;
        assertEquals(ActivityType.RETURN, ActivityType.getByCode(code));
    }
}

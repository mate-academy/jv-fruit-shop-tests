package core.basesyntax.service.functionalityexpansion;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ActivityTypeTest {
    private String code;

    @Test
    void getByCode_invalidCode_NotOk() {
        code = "c";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ActivityType.getByCode(code));
        assertEquals("Unknown activity code: " + code, exception.getMessage(),
                "Incorrect validation error message");
    }

    @Test
    void getByCode_incorrectInputLength_NotOk() {
        code = "cc";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ActivityType.getByCode(code));
        assertEquals("Unknown activity code: " + code, exception.getMessage(),
                "Incorrect validation error message");
    }

    @Test
    void getByCode_activityPurchase_Ok() {
        code = "p";
        assertEquals(ActivityType.PURCHASE, ActivityType.getByCode(code));
    }

    @Test
    void getByCode_activityBalance_Ok() {
        code = "b";
        assertEquals(ActivityType.BALANCE, ActivityType.getByCode(code));
    }

    @Test
    void getByCode_activitySupply_Ok() {
        code = "s";
        assertEquals(ActivityType.SUPPLY, ActivityType.getByCode(code));
    }

    @Test
    void getByCode_activityReturn_Ok() {
        code = "r";
        assertEquals(ActivityType.RETURN, ActivityType.getByCode(code));
    }
}

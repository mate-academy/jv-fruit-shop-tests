package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitOperationTest {
    private String balanceCode = "b";
    private String nonExistCode = "m";

    @Test
    void getFromCode_ok() {
        FruitOperation expected = FruitOperation.BALANCE;
        assertEquals(expected, FruitOperation.getFromCode(balanceCode),
                "Method getFromCode can't find operation");
    }

    @Test
    void getFromCode_notExistCode_notOk() {
        assertThrows(RuntimeException.class, () -> FruitOperation.getFromCode(nonExistCode),
                "Method getFromCode should throw RuntimeException due to unknown code");
    }
}

package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class BalanceTypeHandlerTest {
    private static final BalanceTypeHandler BALANCE_TYPE_HANDLER = new BalanceTypeHandler();
    private static final int EXPECTED_INTEGER = 5;

    @Test
    public void goodInt_Ok() {
        assertEquals(EXPECTED_INTEGER,
                BALANCE_TYPE_HANDLER.getNewQuantity(0, EXPECTED_INTEGER),
                "The handler value does not match the expected one");
    }
}

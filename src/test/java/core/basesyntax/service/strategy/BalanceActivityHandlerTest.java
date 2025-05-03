package core.basesyntax.service.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceActivityHandlerTest {
    private static final int NEGATIVE_QUANTITY_BEFORE = -10;
    private static final int NEGATIVE_QUANTITY_AFTER = -15;
    private static final int VALID_QUANTITY_BEFORE = 50;
    private static final int VALID_QUANTITY_AFTER = 20;
    private BalanceActivityHandler balanceActivityHandler;

    @BeforeEach
    public void setUp() {
        balanceActivityHandler = new BalanceActivityHandler();
    }

    @Test
    public void quantityModify_validBalance_ok() {
        int result = balanceActivityHandler
                .quantityModify(VALID_QUANTITY_BEFORE, VALID_QUANTITY_AFTER);
        assertEquals(VALID_QUANTITY_BEFORE + VALID_QUANTITY_AFTER, result);
    }

    @Test
    public void quantityModify_negativeQuantity_exceptionThrown() {
        assertThrows(RuntimeException.class,
                () -> balanceActivityHandler
                        .quantityModify(NEGATIVE_QUANTITY_BEFORE, NEGATIVE_QUANTITY_AFTER));
    }
}

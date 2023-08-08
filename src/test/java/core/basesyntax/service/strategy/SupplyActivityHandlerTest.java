package core.basesyntax.service.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyActivityHandlerTest {
    private static final int NEGATIVE_QUANTITY_BEFORE = -10;
    private static final int NEGATIVE_QUANTITY_AFTER = -15;
    private static final int VALID_QUANTITY_BEFORE = 50;
    private static final int VALID_QUANTITY_AFTER = 20;
    private SupplyActivityHandler supplyActivityHandler;

    @BeforeEach
    public void setUp() {
        supplyActivityHandler = new SupplyActivityHandler();
    }

    @Test
    public void quantityModify_validSupply_ok() {
        int result = supplyActivityHandler
                .quantityModify(VALID_QUANTITY_BEFORE, VALID_QUANTITY_AFTER);
        assertEquals(VALID_QUANTITY_BEFORE + VALID_QUANTITY_AFTER, result);
    }

    @Test
    public void quantityModify_negativeQuantity_exceptionThrown() {
        assertThrows(RuntimeException.class,
                () -> supplyActivityHandler
                        .quantityModify(NEGATIVE_QUANTITY_BEFORE, NEGATIVE_QUANTITY_AFTER));
    }
}

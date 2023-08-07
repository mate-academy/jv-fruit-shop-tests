package core.basesyntax;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.strategy.PurchaseActivityHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseActivityHandlerTest {
    private static final int NON_VALID_QUANTITY_BEFORE = 10;
    private static final int NON_VALID_QUANTITY_AFTER = 15;
    private static final int VALID_QUANTITY_BEFORE = 50;
    private static final int VALID_QUANTITY_AFTER = 20;
    private PurchaseActivityHandler purchaseActivityHandler;

    @BeforeEach
    public void setUp() {
        purchaseActivityHandler = new PurchaseActivityHandler();
    }

    @Test
    public void testQuantityModify_NotEnoughFruitToPurchase() {
        assertThrows(RuntimeException.class,
                () -> purchaseActivityHandler
                        .quantityModify(NON_VALID_QUANTITY_BEFORE, NON_VALID_QUANTITY_AFTER));
    }

    @Test
    public void testQuantityModify_ValidPurchase() {
        int result = purchaseActivityHandler
                .quantityModify(VALID_QUANTITY_BEFORE, VALID_QUANTITY_AFTER);
        assertEquals(VALID_QUANTITY_BEFORE - VALID_QUANTITY_AFTER, result);
    }
}

package core.basesyntax;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.strategy.PurchaseActivityHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseActivityHandlerTest {
    private static final int QUANTITY_BEFORE = 10;
    private static final int QUANTITY_AFTER = 15;
    private PurchaseActivityHandler purchaseActivityHandler;

    @BeforeEach
    public void setUp() {
        purchaseActivityHandler = new PurchaseActivityHandler();
    }

    @Test
    public void testQuantityModify_NotEnoughFruitToPurchase_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseActivityHandler.quantityModify(QUANTITY_BEFORE, QUANTITY_AFTER));
    }
}

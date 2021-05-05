package core.basesyntax.shopoperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PurchaseTest {

    @Test
    public void calculate_PreviousMoreThanPurchase_Ok() {
        assertEquals(15, new Purchase().calculate(30, 15));
    }

    @Test
    public void calculate_PreviousLessThanPurchase_Bad() {
        try {
            new Purchase().calculate(15, 20);
        } catch (RuntimeException e) {
            assertFalse(false);
        }
    }
}

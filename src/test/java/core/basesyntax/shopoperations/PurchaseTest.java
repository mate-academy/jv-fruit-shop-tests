package core.basesyntax.shopoperations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PurchaseTest {

    @Test
    public void calculate_PreviousMoreThanPurchase_Ok() {
        assertEquals(15, new Purchase().calculate(30, 15));
    }

    @Test(expected = RuntimeException.class)
    public void calculate_PreviousLessThanPurchase_Bad() {
        new Purchase().calculate(15, 20);
    }
}

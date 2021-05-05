package core.basesyntax.shopoperations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BalanceTest {

    @Test
    public void calculate_PreviousZeroPlusValidAmount_Ok() {
        assertEquals(50, new Balance().calculate(0, 50));
    }

    @Test
    public void calculate_PreviousNotZeroPlusValidAmount_Ok() {
        assertEquals(100, new Balance().calculate(50, 50));
    }
}

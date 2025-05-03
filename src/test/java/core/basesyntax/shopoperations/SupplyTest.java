package core.basesyntax.shopoperations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SupplyTest {

    @Test
    public void calculate_ValidAmount_Ok() {
        assertEquals(50, new Supply().calculate(35, 15));
    }
}

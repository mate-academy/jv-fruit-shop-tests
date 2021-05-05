package core.basesyntax.shopoperations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReturnTest {

    @Test
    public void calculate_ValidAmount_Ok() {
        assertEquals(50, new Return().calculate(35, 15));
    }
}

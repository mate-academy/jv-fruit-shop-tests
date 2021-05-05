package core.basesyntax.shopoperations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListOfOperationsTest {

    @Test
    public void contains_InvalidOperation_Bad() {
        boolean actual;
        try {
            actual = ListOfOperations.contains("N");
        } catch (IllegalArgumentException e) {
            actual = false;
        }
        assertFalse(actual);
    }

    @Test
    public void contains_ValidOperation_Ok() {
        assertTrue(ListOfOperations.contains("B"));
    }
}

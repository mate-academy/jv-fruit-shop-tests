package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ProductTest {
    private static final String NAME = "banana";
    private static Product testProduct;

    @BeforeClass
    public static void beforeClass() throws Exception {
        testProduct = new Product(NAME);
    }

    @Test
    public void getNameTest_Ok() {
        String actual = testProduct.getName();
        assertEquals(NAME, actual);
    }

    @Test
    public void equalsTest_Ok() {
        assertTrue(testProduct.equals(new Product(NAME)));
        assertTrue(testProduct.equals(testProduct));
        assertFalse(testProduct.equals(null));
        assertFalse(testProduct.equals(new Object()));
    }

    @Test
    public void compareToTestEquals_Ok() {
        int actual = testProduct.compareTo(new Product(NAME));
        assertEquals(0, actual);
    }

    @Test
    public void compareToTestLess_Ok() {
        int actual = testProduct.compareTo(new Product("apple"));
        assertEquals(1, actual);
    }

    @Test
    public void compareToTestMore_Ok() {
        int actual = testProduct.compareTo(new Product("cabbage"));
        assertEquals(-1, actual);
    }
}

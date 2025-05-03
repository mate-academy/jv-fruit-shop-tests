package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.services.exception.ProductException;
import org.junit.Test;

public class ProductTest {

    @Test(expected = ProductException.class)
    public void create_NullName_NotOk() {
        Product product = new Product(null, 100);
    }

    @Test(expected = ProductException.class)
    public void create_NegativeCount_NotOk() {
        Product product = new Product("banana", -1);
    }

    @Test(expected = ProductException.class)
    public void set_NullName_NotOk() {
        Product product = new Product("", 5);
        product.setName(null);
    }

    @Test(expected = ProductException.class)
    public void set_NegativeCount_NotOk() {
        Product product = new Product("", 5);
        product.setCount(-1);
    }

    @Test
    public void create_correctNameAndCount_ok() {
        Product product = new Product("banana", 100);
    }

    @Test
    public void getName_ok() {
        Product product = new Product("banana", 100);
        assertEquals("banana", product.getName());
    }

    @Test
    public void getCount_ok() {
        Product product = new Product("banana", 100);
        assertEquals(100, product.getCount());
    }

    @Test
    public void equals_sameProducts_ok() {
        Product product1 = new Product("banana", 100);
        Product product2 = new Product("banana", 100);
        assertEquals(product1, product2);
    }

    @Test
    public void equals_differentProducts_ok() {
        Product product1 = new Product("banana", 100);
        Product product3 = new Product("apple", 5);
        assertNotEquals(product3, product1);
    }

    @Test
    public void equals_null_NotOk() {
        Product product1 = new Product("banana", 100);
        assertFalse(product1.equals(null));
    }

    @Test
    public void setName_validName_ok() {
        Product product = new Product("banana", 100);
        product.setName("apple");
        assertEquals("apple", product.getName());
    }

    @Test
    public void setCount_validCount_ok() {
        Product product = new Product("banana", 100);
        product.setCount(5);
        assertEquals(5, product.getCount());
    }
}

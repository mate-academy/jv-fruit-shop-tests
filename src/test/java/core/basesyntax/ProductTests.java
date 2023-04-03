package core.basesyntax;

import core.basesyntax.model.Product;
import core.basesyntax.services.exception.ProductException;
import static org.junit.Assert.*;

import org.junit.Test;

public class ProductTests {

    @Test(expected = ProductException.class)
    public void productWithNullName_NotOk() {
        Product product = new Product(null, 100);
    }

    @Test(expected = ProductException.class)
    public void productWithNegativeCount_NotOk() {
        Product product = new Product("banana", -1);
    }

    @Test(expected = ProductException.class)
    public void setNullNameInProduct_NotOk() {
        Product product = new Product("", 5);
        product.setName(null);
    }

    @Test(expected = ProductException.class)
    public void setNegativeCountInProduct_NotOk() {
        Product product = new Product("", 5);
        product.setCount(-1);
    }

    @Test
    public void validProduct_Ok() {
        Product product = new Product("banana", 100);
    }

    @Test
    public void getProductName_Ok() {
        Product product = new Product("banana", 100);
        assertEquals("banana", product.getName());
    }

    @Test
    public void getProductQuantity_Ok() {
        Product product = new Product("banana", 100);
        assertEquals(100, product.getCount());
    }

    @Test
    public void equals_Ok() {
        Product product1 = new Product("banana", 100);
        Product product2 = new Product("banana", 100);
        Product product3 = new Product("apple", 5);
        assertEquals(product1, product2);
        assertNotEquals(product3, product1);
    }

    @Test
    public void setters_ok() {
        Product product = new Product("banana", 100);
        product.setCount(5);
        product.setName("apple");
        Product expected = new Product("apple", 5);
        assertEquals(expected, product);
    }
}

package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.ValidationException;
import org.junit.Test;

public class FruitTest {
    private Fruit expected = new Fruit("banana");

    @Test(expected = ValidationException.class)
    public void getName_null_notOk() {
        Fruit actual = new Fruit(null);
    }

    @Test
    public void getName_notNull_Ok() {
        Fruit actual = new Fruit("banana");
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testEquals_equalObject_Ok() {
        Fruit actual = new Fruit("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void testEquals_unequalObjects_Ok() {
        Fruit actual = new Fruit("baNAna");
        assertNotEquals(expected, actual);
    }

    @Test
    public void testHashCode_equalHashcode_Ok() {
        Fruit fruit = new Fruit("banana");
        int actualHashCode = fruit.hashCode();
        int expectedHashCode = expected.hashCode();
        assertEquals(expectedHashCode, actualHashCode);
    }

    @Test
    public void testToString() {
        String expectedString = "Fruit{name='banana'}";
        Fruit fruit = new Fruit("banana");
        assertEquals(expectedString, fruit.toString());
    }
}

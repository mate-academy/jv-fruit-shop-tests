package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTest {
    @Test
    void toString_correctFruit_Ok() {
        Fruit fruit = new Fruit("banana");
        String expected = "Fruit{name='banana'}";
        String actual = fruit.toString();
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void equals_EqualFruits_Ok() {
        Fruit first = new Fruit("apple");
        Fruit second = new Fruit("apple");
        Assertions.assertEquals(first, second);
    }

    @Test
    void equals_NotEqualFruits_NotOk() {
        Fruit first = new Fruit("apple");
        Fruit second = new Fruit("banana");
        Assertions.assertNotEquals(first, second);
    }
}

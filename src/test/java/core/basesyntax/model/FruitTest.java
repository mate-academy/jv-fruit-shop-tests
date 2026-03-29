package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTest {

    @Test
    void equals_fruitsAreSame_ok() {
        Fruit fruit = Fruit.of("banana", 20);
        Fruit secondFruit = Fruit.of("banana", 20);
        Assertions.assertEquals(fruit, secondFruit);
        Assertions.assertEquals(fruit.hashCode(), secondFruit.hashCode());
    }

    @Test
    void equals_comparedWithNull_notOk() {
        Fruit fruit = Fruit.of("banana", 20);
        Assertions.assertNotEquals(null, fruit);
    }

    @Test
    void equals_comparedWithDifferentObject_notOk() {
        Fruit fruit = Fruit.of("banana", 20);
        Assertions.assertNotEquals("string object", fruit);
    }

    @Test
    void equals_fruitNameIsNull_notOk() {
        Fruit fruit = Fruit.of(null, 20);
        Assertions.assertNull(fruit.getName());
    }

    @Test
    void equals_fruitsHaveDifferentName_notOk() {
        Fruit fruit = Fruit.of("banana", 20);
        Fruit secondFruit = Fruit.of("orange", 20);
        Assertions.assertNotEquals(fruit, secondFruit);
        Assertions.assertNotEquals(fruit.hashCode(), secondFruit.hashCode());
    }

    @Test
    void equals_fruitsHaveDifferentQuantity_ok() {
        Fruit fruit = Fruit.of("banana", 10);
        Fruit secondFruit = Fruit.of("banana", 20);
        Assertions.assertEquals(fruit, secondFruit);
        Assertions.assertEquals(fruit.hashCode(), secondFruit.hashCode());
    }

    @Test
    void toString_containsData_ok() {
        String result = Fruit.of("banana", 20).toString();
        Assertions.assertTrue(result.contains("banana"));
        Assertions.assertTrue(result.contains("20"));
    }

    @Test
    void getters_returnCorrectValues_ok() {
        Fruit fruit = Fruit.of("banana", 20);
        Assertions.assertEquals("banana", fruit.getName());
        Assertions.assertEquals(20, fruit.getQuantity());
    }
}

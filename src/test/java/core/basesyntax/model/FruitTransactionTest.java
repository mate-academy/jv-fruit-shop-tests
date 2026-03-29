package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void equals_fruitsAreSame_ok() {
        FruitTransaction fruit = FruitTransaction.of(Operation.B, "banana", 20);
        FruitTransaction secondFruit = FruitTransaction.of(Operation.B, "banana", 20);
        Assertions.assertEquals(fruit, secondFruit);
        Assertions.assertEquals(fruit.hashCode(), secondFruit.hashCode());
    }

    @Test
    void equals_comparedWithNull_notOk() {
        FruitTransaction fruit = FruitTransaction.of(Operation.B, "banana", 20);
        Assertions.assertNotEquals(null, fruit);
    }

    @Test
    void equals_comparedWithDifferentObject_notOk() {
        FruitTransaction fruit = FruitTransaction.of(Operation.B, "banana", 20);
        Assertions.assertNotEquals("string object", fruit);
    }

    @Test
    void equals_fruitNameIsNull_notOk() {
        FruitTransaction fruit = FruitTransaction.of(Operation.B, null, 20);
        Assertions.assertNull(fruit.getFruitName());
    }

    @Test
    void equals_fruitOperationIsNull_notOk() {
        FruitTransaction fruit = FruitTransaction.of(null, "banana", 20);
        Assertions.assertNull(fruit.getOperation());
    }

    @Test
    void equals_fruitsHaveDifferentName_notOk() {
        FruitTransaction fruit = FruitTransaction.of(Operation.B, "banana", 20);
        FruitTransaction secondFruit = FruitTransaction.of(Operation.B, "orange", 20);
        Assertions.assertNotEquals(fruit, secondFruit);
        Assertions.assertNotEquals(fruit.hashCode(), secondFruit.hashCode());
    }

    @Test
    void equals_fruitsHaveDifferentQuantity_notOk() {
        FruitTransaction fruit = FruitTransaction.of(Operation.B, "banana", 20);
        FruitTransaction secondFruit = FruitTransaction.of(Operation.B, "banana", 30);
        Assertions.assertNotEquals(fruit, secondFruit);
        Assertions.assertNotEquals(fruit.hashCode(), secondFruit.hashCode());
    }

    @Test
    void equals_fruitsHaveDifferentOperations_notOk() {
        FruitTransaction fruit = FruitTransaction.of(Operation.B, "banana", 20);
        FruitTransaction secondFruit = FruitTransaction.of(Operation.P, "banana", 20);
        Assertions.assertNotEquals(fruit, secondFruit);
        Assertions.assertNotEquals(fruit.hashCode(), secondFruit.hashCode());
    }

    @Test
    void toString_containsData_ok() {
        String result = FruitTransaction.of(Operation.B, "banana", 20).toString();
        Assertions.assertTrue(result.contains("b"));
        Assertions.assertTrue(result.contains("banana"));
        Assertions.assertTrue(result.contains("20"));
    }

    @Test
    void getters_returnCorrectValues_ok() {
        FruitTransaction fruit = FruitTransaction.of(Operation.B,"banana", 20);
        Assertions.assertEquals("banana", fruit.getFruitName());
        Assertions.assertEquals(20, fruit.getQuantity());
        Assertions.assertEquals(Operation.B, fruit.getOperation());
    }
}

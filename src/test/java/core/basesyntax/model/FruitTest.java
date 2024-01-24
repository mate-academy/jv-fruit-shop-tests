package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTest {
    @Test
    void testGettersAndSetters() {
        Fruit fruit = new Fruit();
        fruit.setOperation(Fruit.Operation.BALANCE);
        fruit.setFruit("apple");
        assertEquals(Fruit.Operation.BALANCE, fruit.getOperation());
        assertEquals("apple", fruit.getFruit());
    }
}

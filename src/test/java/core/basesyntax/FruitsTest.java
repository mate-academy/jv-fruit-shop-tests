package core.basesyntax;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitsTest {
    private Fruits fruits;

    @BeforeEach
    void setUp() {
        fruits = new Fruits();
    }

    @Test
    void shouldSetCorrectValue() {
        int pineapple = fruits.balance("pineapple", 100);
        assertEquals(100,pineapple);
    }
 
    @Test
    void shouldAddCorrectSupply() {
        fruits.balance("apple", 50);
        int apple = fruits.supply("apple",50);
        assertEquals(100,apple);
    }
 
    @Test
    void shouldDifferentiateCorrectValue() {
        fruits.balance("banana",400);
        assertEquals(200,fruits.purchase("banana",200));
    }

    @Test
    void shouldAddCorrectReturnedValue() {
        fruits.balance("orange",300);
        int returnFruits = fruits.returnFruit("orange",100);
        assertEquals(400,returnFruits);
    }
}

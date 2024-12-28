package core.basesyntax;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FruitsTest {
    private Fruits fruits;

    @BeforeEach
    void setUp() {
        fruits = new Fruits();
    }

    @Test
    void balance() {
        int pineapple = fruits.balance("pineapple", 100);
        assertEquals(100,pineapple);
    }

    @Test
    void supply() {
        fruits.balance("apple", 50);
        int apple = fruits.supply("apple",50);
        assertEquals(100,apple);
    }

    @Test
    void purchase() {
        fruits.balance("banana",400);
        assertEquals(200,fruits.purchase("banana",200));
    }

    @Test
    void returnFruit() {
        fruits.balance("orange",300);
        int returnFruits = fruits.returnFruit("orange",100);
        assertEquals(400,returnFruits);
    }
}
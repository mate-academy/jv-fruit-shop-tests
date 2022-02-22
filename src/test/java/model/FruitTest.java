package model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

public class FruitTest {
    private static final String name = "banana";
    private static final int amount = 24;
    private static final Fruit validFruit
            = new Fruit(name, amount);

    @Test
    public void fruitConstructor_Ok() {
        Fruit fruit = new Fruit(name, amount);
        boolean expected = true;
        boolean actual = fruit.getName().equals(name)
                && fruit.getAmount() == amount;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fruitConstructor_nullName_Exception() {
        Fruit fruit = new Fruit(null, amount);
        boolean expected = false;
        boolean actual = fruit.getName().equals(name)
                && fruit.getAmount() == amount;
        assertEquals(expected, actual);
    }

    @Test
    public void setAmount_Ok() {
        int expected = amount;
        int actual = validFruit.getAmount();
        assertEquals(expected, actual);
    }

    @Test
    public void setName_Ok() {
        boolean expected = true;
        String name = "orange";
        validFruit.setName(name);
        boolean actual = validFruit.getName().equals(name);
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        validFruit.setName(name);
        validFruit.setAmount(amount);
    }
}

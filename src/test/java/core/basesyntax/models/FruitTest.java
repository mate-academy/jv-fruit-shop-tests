package core.basesyntax.models;

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

    @Test(expected = RuntimeException.class)
    public void fruitConstructor_minusAmount_Exception() {
        Fruit fruit = new Fruit(name, -3);
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

    @Test(expected = RuntimeException.class)
    public void setAmount_MinusValue_Exception() {
        validFruit.setAmount(-3);
    }

    @Test
    public void setName_Ok() {
        boolean expected = true;
        String name = "orange";
        validFruit.setName(name);
        boolean actual = validFruit.getName().equals(name);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void setName_null_Exception() {
        validFruit.setName(null);
    }

    @Test(expected = RuntimeException.class)
    public void setName_EmptyString_Exception() {
        validFruit.setName("");
    }

    @Test
    public void equals_sameObject_Ok() {
        Fruit fruit = new Fruit(name, amount);
        boolean expected = true;
        boolean actual = fruit.equals(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void equals_null_Ok() {
        Fruit fruit = new Fruit(name, amount);
        boolean expected = false;
        boolean actual = fruit.equals(null);
        assertEquals(expected, actual);
    }

    @Test
    public void equals_Ok() {
        Fruit fruit = new Fruit(name, amount);
        boolean expected = true;
        boolean actual = fruit.equals(validFruit);
        assertEquals(expected, actual);
    }

    @Test
    public void clone_Ok() {
        Fruit fruit = validFruit.clone();
        boolean expected = true;
        boolean actual = fruit != validFruit;
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        validFruit.setName(name);
        validFruit.setAmount(amount);
    }
}

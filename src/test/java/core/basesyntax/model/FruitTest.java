package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FruitTest {
    private Fruit fruit;

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit("banana");
    }

    @Test
    public void fruitTest_name_Ok() {
        String actual = fruit.getName();
        String expected = "banana";
        assertEquals(actual, expected);
    }
}

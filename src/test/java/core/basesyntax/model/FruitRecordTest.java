package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitRecordTest {
    private Fruit testFruit = new Fruit("test");
    private FruitRecord fruitRecordExpected = new FruitRecord("b", testFruit, 10);

    @Test
    public void getOperation_validEntry_Ok() {
        String expectedOperation = fruitRecordExpected.getOperation();
        FruitRecord test = new FruitRecord("b", new Fruit("test"), 10);
        String actual = test.getOperation();
        assertEquals(expectedOperation, actual);
    }

    @Test
    public void getFruit_checkValidValue_Ok() {
        Fruit expected = fruitRecordExpected.getFruit();
        FruitRecord test = new FruitRecord("b", new Fruit("test"), 10);
        assertEquals(expected, test.getFruit());
    }
}

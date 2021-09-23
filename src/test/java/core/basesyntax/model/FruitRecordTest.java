package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordTest {
    private static FruitRecord fruitRecord;

    @BeforeClass
    public static void setUp() throws Exception {
        Fruit testFruit = new Fruit("test");
        fruitRecord = new FruitRecord("b", testFruit, 10);
    }

    @Test
    public void getOperation_validEntry_Ok() {
        String expectedOperation = "b";
        String actualOperation = fruitRecord.getOperation();
        assertEquals(expectedOperation, actualOperation);
    }

    @Test
    public void getFruit_checkValidValue_Ok() {
        Fruit expected = new Fruit("test");
        assertEquals(expected, fruitRecord.getFruit());
    }
}

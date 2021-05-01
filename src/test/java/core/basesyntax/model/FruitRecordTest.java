package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordTest {

    private static FruitRecord fruitRecord;
    private static final String APPLE = "apple";
    private static final int QUANTITY = 100;
    private static final OperationType operationType = OperationType.B;
    private static Fruit fruit;

    @BeforeClass
    public static void setUp() {
        fruit = new Fruit(APPLE, QUANTITY);
        fruitRecord = new FruitRecord(fruit, operationType);
    }

    @Test
    public void testGetFruit() {
        Fruit actual = fruitRecord.getFruit();
        assertEquals(fruit, actual);
    }

    @Test
    public void testGetOperationType() {
        assertEquals(operationType, fruitRecord.getOperationType());
    }

}
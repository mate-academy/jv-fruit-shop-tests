package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitRecordTest {
    private static final FruitRecord validFruitRecord = new FruitRecord('b', "banana", 10);

    @Test
    public void setAmount_Ok() {
        FruitRecord fruitRecord
                = new FruitRecord('b',"banana", 10);
        fruitRecord.setAmount(11);
        int expected = 11;
        int actual = fruitRecord.getAmount();
        assertEquals(expected,actual);
    }

    @Test
    public void equals_sameObject_Ok() {
        FruitRecord fruitRecord
                = new FruitRecord('b',"banana", 10);
        boolean expected = true;
        boolean actual = fruitRecord.equals(fruitRecord);
        assertEquals(expected, actual);
    }

    @Test
    public void equals_Ok() {
        FruitRecord fruitRecord
                = new FruitRecord('b',"banana", 10);
        boolean expected = true;
        boolean actual = fruitRecord.equals(validFruitRecord);
        assertEquals(expected, actual);
    }
}

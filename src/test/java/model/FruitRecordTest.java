package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitRecordTest {
    @Test
    public void setAmount_Ok() {
        FruitRecord fruitRecord
                = new FruitRecord('b',"banana", 10);
        fruitRecord.setAmount(11);
        int expected = 11;
        int actual = fruitRecord.getAmount();
        assertEquals(expected,actual);
    }
}

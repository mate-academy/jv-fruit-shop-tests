package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import org.junit.Assert;
import org.junit.Test;

public class FruitRecordModelTest {
    private static final FruitRecord.Type BALANCE = FruitRecord.Type.BALANCE;
    private static final FruitRecord.Type SUPPLY = FruitRecord.Type.SUPPLY;

    @Test
    public void checkFruitRecordsHashCodeEquality_hashCode_OK() {
        FruitRecord fruitrecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord newFruitRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        int expected = fruitrecord.hashCode();
        int actual = newFruitRecord.hashCode();
        Assert.assertEquals("Test failed! Fruit records' hashcodes should be equal!",
                expected, actual);
    }

    @Test
    public void checkFruitRecordsHashCodeEquality_hashCode_Not_OK() {
        FruitRecord fruitrecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord newFruitRecord = new FruitRecord(15, SUPPLY, new Fruit("apple"));
        int expected = fruitrecord.hashCode();
        int actual = newFruitRecord.hashCode();
        Assert.assertNotEquals("Test failed! Fruit records' hashcodes should be unequal!",
                expected, actual);
    }

    @Test
    public void checkFruitRecordsEquality_equals_OK() {
        FruitRecord fruitrecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord newFruitRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        Assert.assertEquals("Test failed! Fruit records should be equal!",
                fruitrecord, newFruitRecord);
    }

    @Test
    public void checkFruitRecordsEquality_equals_Not_OK() {
        FruitRecord fruitrecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        FruitRecord newFruitRecord = new FruitRecord(15, SUPPLY, new Fruit("apple"));
        Assert.assertNotEquals("Test failed! Fruit records should be unequal!",
                fruitrecord, newFruitRecord);
    }
}

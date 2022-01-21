package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoTest {
    private static final FruitRecord.Type BALANCE = FruitRecord.Type.BALANCE;
    private static final FruitRecord.Type SUPPLY = FruitRecord.Type.SUPPLY;
    private static final FruitRecord.Type RETURN = FruitRecord.Type.RETURN;
    private static final FruitRecord.Type PURCHASE = FruitRecord.Type.PURCHASE;
    private static final List<FruitRecord> DATA_BASE = DataBase.DB;
    private static List<FruitRecord> fruitRecords;
    private static FruitDao fruitDao;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitRecords = new ArrayList<>();
        fruitRecords.add(new FruitRecord(20, BALANCE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(100, BALANCE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(100, SUPPLY, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(13, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(10, RETURN, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(20, PURCHASE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(5, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(50, SUPPLY, new Fruit("banana")));
    }

    @Test
    public void addNewRecordToDataBase_addRecord_OK() {
        FruitRecord newRecord = new FruitRecord(20, BALANCE, new Fruit("banana"));
        Assert.assertTrue("Test failed! Expected true!",
                fruitDao.addRecord(newRecord));
        List<FruitRecord> expected = Arrays.asList(newRecord);
        List<FruitRecord> actual = DATA_BASE;
        Assert.assertEquals("Test failed! Expected record differs from actual record!",
                expected, actual);
    }

    @Test
    public void getAllRecordsFromDataBase_OK() {
        FruitRecord newRecord = new FruitRecord(21, BALANCE, new Fruit("banana"));
        List<FruitRecord> expected = new ArrayList<>(fruitRecords);
        expected.remove(5);
        expected.set(0, newRecord);
        DATA_BASE.addAll(fruitRecords);
        DATA_BASE.remove(5);
        DATA_BASE.set(0, newRecord);
        List<FruitRecord> actual = fruitDao.getRecords();
        Assert.assertEquals("Test failed! Expected records differ from actual records!",
                expected, actual);
    }

    @After
    public void afterEachTest() {
        DATA_BASE.clear();
    }
}

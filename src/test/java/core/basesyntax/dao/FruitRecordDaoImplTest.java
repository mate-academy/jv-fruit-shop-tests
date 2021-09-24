package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDaoImplTest {
    private static List<FruitRecord> expected;
    private static FruitRecordDao fruitRecordDao;
    private static List<FruitRecord> actual;

    @BeforeClass
    public static void setUp() {
        fruitRecordDao = new FruitRecordDaoImpl();
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        expected.add(new FruitRecord("b", "banana", 10));
        expected.add(new FruitRecord("s", "banana", 10));
        expected.add(new FruitRecord("p", "banana", 15));
        expected.add(new FruitRecord("r", "banana", 5));
    }

    @After
    public void everyTest() {
        Storage.fruitRecordList.clear();
        Storage.fruitMap.clear();
    }

    @Test
    public void checkListOfFruitRecordsAdd_Ok() {
        fruitRecordDao.saveAll(expected);
        actual = Storage.fruitRecordList;
        assertEquals(expected, actual);
    }

    @Test
    public void checkListOfFruitRecords_NotOk() {
        fruitRecordDao.saveAll(expected);
        Storage.fruitRecordList.remove(0);
        actual = Storage.fruitRecordList;
        assertNotEquals(expected, actual);
    }

    @Test
    public void checkListOfFruitRecordsGet_Ok() {
        Storage.fruitRecordList.addAll(expected);
        actual = fruitRecordDao.getAll();
        assertEquals(expected, actual);
    }
}

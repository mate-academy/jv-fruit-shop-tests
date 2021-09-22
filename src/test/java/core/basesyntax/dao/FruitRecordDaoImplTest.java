package core.basesyntax.dao;

import static org.junit.gen5.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitRecordDaoImplTest {
    private static List<FruitRecord> expected;
    private static FruitRecordDao fruitRecordDao;
    private static List<FruitRecord> actual;

    @BeforeAll
    static void setUp() {
        fruitRecordDao = new FruitRecordDaoImpl();
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        expected.add(new FruitRecord("b", "banana", 10));
        expected.add(new FruitRecord("s", "banana", 10));
        expected.add(new FruitRecord("p", "banana", 15));
        expected.add(new FruitRecord("r", "banana", 5));
    }

    @Test
    void checkListOfFruitRecordsAdd_Ok() {
        fruitRecordDao.saveAll(expected);
        actual = Storage.fruitRecordList;
        assertEquals(expected, actual);
    }

    @Test
    void checkListOfFruitRecordsGet_Ok() {
        Storage.fruitRecordList.clear();
        Storage.fruitRecordList.addAll(expected);
        actual = fruitRecordDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void checkListOfFruitRecords_NotOk() {
        fruitRecordDao.saveAll(expected);
        actual = Storage.fruitRecordList;
        actual.remove(0);
        assertNotEquals(expected, actual);
    }
}

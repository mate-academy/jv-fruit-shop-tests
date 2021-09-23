package core.basesyntax.dao;

import core.basesyntax.database.Database;
import core.basesyntax.model.Record;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RecordDaoTest {
    private static RecordDao recordDao;

    @BeforeClass
    public static void beforeAll() {
        recordDao = new RecordDaoImpl();
    }

    @Test
    public void addRecord_correctData_Ok() {
        Record testRecord = new Record("b", "apple", 100);
        recordDao.addRecord(testRecord);
        List<Record> expected = List.of(testRecord);
        List<Record> actual = Database.RECORDS;
        Assert.assertEquals("Expected and actual records "
                + "differ from each other!", expected, actual);
    }

    @Test
    public void getRecords_correctData_Ok() {
        Record testRecord1 = new Record("b", "apple", 20);
        Record testRecord2 = new Record("b", "banana", 30);
        Record testRecord3 = new Record("b", "fruit", 40);
        Database.RECORDS.add(testRecord1);
        Database.RECORDS.add(testRecord2);
        Database.RECORDS.add(testRecord3);
        List<Record> expected = List.of(testRecord1, testRecord2, testRecord3);
        List<Record> actual = recordDao.getRecords();
        Assert.assertEquals("Expected and actual records "
                + "differ from each other!", expected, actual);
    }

    @After
    public void tearDown() {
        Database.RECORDS.clear();
    }
}

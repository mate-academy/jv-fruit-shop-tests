package core.basesyntax.dao;

import core.basesyntax.database.Database;
import core.basesyntax.model.Record;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class RecordDaoTest {
    private static final int ADD_CORRECT_DB_SIZE = 1;
    private static final int GET_CORRECT_DB_SIZE = 3;
    private static RecordDao recordDao;

    @BeforeClass
    public static void beforeAll() {
        recordDao = new RecordDaoImpl();
    }

    @Test
    public void addRecord_correctData_Ok() {
        Record testRecord = new Record("b", "apple", 100);
        recordDao.addRecord(testRecord);
        int expected = ADD_CORRECT_DB_SIZE;
        int actual = Database.RECORDS.size();
    }

    @Test
    public void getRecords_Ok() {
        Database.RECORDS.add(new Record("b", "apple", 100));
        Database.RECORDS.add(new Record("b", "banana", 20));
        Database.RECORDS.add(new Record("s", "apple", 60));
        List<Record> testRecords = recordDao.getRecords();
        int actual = testRecords.size();
        int expected = GET_CORRECT_DB_SIZE;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Database.RECORDS.clear();
    }
}
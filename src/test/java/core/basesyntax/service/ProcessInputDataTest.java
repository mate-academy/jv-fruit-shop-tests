package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ProcessInputDataTest {
    private static ProcessInputData processInputData;

    @Before
    public void setUp() throws Exception {
        processInputData = new ProcessInputData();
    }

    @Test
    public void processInput_workingWithData_True() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[] {"b", "banana", "10"});
        testData.add(new String[] {"p", "banana", "5"});
        testData.add(new String[] {"s", "banana", "10"});
        testData.add(new String[] {"r", "banana", "20"});
        testData.add(new String[] {"b", "apple", "20"});
        testData.add(new String[] {"p", "apple", "5"});
        processInputData.processInput(testData);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        assertTrue(storageDao.containsKey("banana"));
        assertTrue(storageDao.containsKey("apple"));
        assertEquals(storageDao.getAmount("banana"), 35);
        assertEquals(storageDao.getAmount("apple"),15);

    }
}

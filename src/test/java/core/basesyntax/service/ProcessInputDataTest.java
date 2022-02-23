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

    @Test(expected = RuntimeException.class)
    public void processInput_incorrectOperation_Exception() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[] {"", "banana", "10"});
        processInputData.processInput(testData);
    }

    @Test
    public void processInput_workingWithData_True() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[] {"b", "banana", "10"});
        testData.add(new String[] {"p", "banana", "5"});
        testData.add(new String[] {"s", "banana", "10"});
        testData.add(new String[] {"r", "banana", "20"});
        testData.add(new String[] {"b", "someFruit", "20"});
        testData.add(new String[] {"p", "someFruit", "5"});
        processInputData.processInput(testData);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        assertTrue(storageDao.containsKey("banana"));
        assertTrue(storageDao.containsKey("someFruit"));
        assertEquals(storageDao.getAmount("banana"), 35);
        assertEquals(storageDao.getAmount("someFruit"),15);

    }
}

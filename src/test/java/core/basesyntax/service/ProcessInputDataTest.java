package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessInputDataTest {
    private static ProcessInputData processInputData;

    @BeforeClass
    public static void beforeClass() throws Exception {
        processInputData = new ProcessInputData();
    }

    @Test(expected = RuntimeException.class)
    public void incorrectOperation_Exception() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[] {"", "apple", "10"});
        processInputData.processInput(testData);
    }
    /*
        @Test(expected = RuntimeException.class)
        public void incorrectFruit_Exception() {
            List<String[]> testData = new ArrayList<>();
            testData.add(new String[] {"b", "", "10"});
            processInputData.processInput(testData);
        }

        @Test(expected = RuntimeException.class)
        public void amountIsEmpty_Exception() {
            List<String[]> testData = new ArrayList<>();
            testData.add(new String[] {"b", "banana", ""});
            processInputData.processInput(testData);
        }

        @Test(expected = RuntimeException.class)
        public void incorrectAmount_Exception() {
            List<String[]> testData = new ArrayList<>();
            testData.add(new String[] {"b", "banana", "a"});
            processInputData.processInput(testData);
        }
    */
    @Test
    public void workingWithData_True() {
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

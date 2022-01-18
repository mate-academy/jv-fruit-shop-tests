package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;

public class RemnantsDaoMapTest {

    private final RemnantsDao remnantsDao = new RemnantsDaoMap();

    @AfterClass
    public static void afterClass() {
        Storage.fruitsRemnants.clear();
    }

    @Test
    public void fruitIsPresentInStorage_ok() {
        remnantsDao.addFruitRemnant("mango", 5L);
        boolean actual = remnantsDao.fruitIsPresentInStorage("mango");
        assertTrue(actual);
    }

    @Test
    public void add_get_fruitRemnant_ok() {
        long fruitRemnantExpected = 20L;
        remnantsDao.addFruitRemnant("lemon", fruitRemnantExpected);
        long fruitRemnantActual = remnantsDao.getFruitRemnant("lemon");
        assertEquals(fruitRemnantExpected, fruitRemnantActual);
    }

    @Test
    public void add_update_fruitRemnant_ok() {
        remnantsDao.addFruitRemnant("orange", 25L);
        long fruitRemnantExpected = 28L;
        remnantsDao.updateFruitRemnant("orange", fruitRemnantExpected);
        long fruitRemnantActual = remnantsDao.getFruitRemnant("orange");
        assertEquals(fruitRemnantExpected, fruitRemnantActual);
    }

    @Test
    public void add_getReportListFromDB_ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Avocado,33");
        expectedList.add("Grapefruit,34");
        expectedList.add("Kiwi,35");
        Storage.fruitsRemnants.clear();
        remnantsDao.addFruitRemnant("Avocado", 33L);
        remnantsDao.addFruitRemnant("Grapefruit", 34L);
        remnantsDao.addFruitRemnant("Kiwi", 35L);
        List<String> reportList = remnantsDao.getRemnantsReportList();
        Collections.sort(reportList);
        assertEquals(expectedList, reportList);
    }

    @Test
    public void add_getinputListFromDB_ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("b,Avocado,33");
        expectedList.add("b,Grapefruit,34");
        expectedList.add("b,Kiwi,35");
        Storage.fruitsRemnants.clear();
        remnantsDao.addFruitRemnant("Avocado", 33L);
        remnantsDao.addFruitRemnant("Grapefruit", 34L);
        remnantsDao.addFruitRemnant("Kiwi", 35L);
        List<String> inputList = remnantsDao.getRemnantsInputList();
        Collections.sort(inputList);
        assertEquals(expectedList, inputList);
    }
}

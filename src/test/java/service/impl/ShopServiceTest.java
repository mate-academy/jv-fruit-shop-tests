package service.impl;

import static org.junit.Assert.assertArrayEquals;

import dao.FruitDao;
import dao.FruitDaoImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.ShopService;

public class ShopServiceTest {
    private static FruitDao fruitDao;
    private static ShopService shopService;
    private static Map<String, Integer> storage;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        storage = new HashMap<>();
        fruitDao = new FruitDaoImpl(storage);
        shopService = new ShopServiceImpl(fruitDao);
    }

    @Test
    public void doReport_getReportFromDB_ok() {
        final String message1 = "there had to be head message in the report but it wasn't";
        final String message2 = "failed output data.\n";
        fruitDao.add("apples", 100);
        fruitDao.add("oranges", 100);
        fruitDao.add("nuts", 100);
        List<String[]> reportStrings = shopService.doReport();
        assertArrayEquals(message1, reportStrings.get(0), new String[]{"fruit", "quantity"});
        assertArrayEquals(message2, reportStrings.get(1), new String[]{"oranges", "100"});
        assertArrayEquals(message2, reportStrings.get(2), new String[]{"nuts", "100"});
        assertArrayEquals(message2, reportStrings.get(3), new String[]{"apples", "100"});
        storage.clear();
    }
}

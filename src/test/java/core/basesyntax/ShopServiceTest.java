package core.basesyntax;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import dao.FruitDao;
import dao.FruitDaoImpl;
import db.Storage;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.ShopService;
import service.impl.ShopServiceImpl;

public class ShopServiceTest {
    private static FruitDao fruitDao;
    private static ShopService shopService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        fruitDao = new FruitDaoImpl();
        shopService = new ShopServiceImpl(fruitDao);
    }

    @Test
    public void doReport_getReportFromDB_ok() {
        final String message1 = "there had to be head message in the report but it wasn't";
        final String message2 = "failed output data.\n";
        Storage.fruits.clear();
        fruitDao.add("apples", 100);
        fruitDao.add("oranges", 100);
        fruitDao.add("nuts", 100);
        List<String[]> reportStrings = shopService.doReport();
        assertEquals(message1, reportStrings.get(0), new String[]{"fruit", "quantity"});
        assertArrayEquals(message2, reportStrings.get(1), new String[]{"oranges", "100"});
        assertArrayEquals(message2, reportStrings.get(2), new String[]{"nuts", "100"});
        assertArrayEquals(message2, reportStrings.get(3), new String[]{"apples", "100"});
        Storage.fruits.clear();
    }
}

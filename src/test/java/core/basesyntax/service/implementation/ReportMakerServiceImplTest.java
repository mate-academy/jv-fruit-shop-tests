package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportMakerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceImplTest {
    private static ReportMakerService reportMakerService;
    private static FruitDao fruitDao;
    private static List<String> expected;
    private static List<String> actual;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        reportMakerService = new ReportMakerServiceImpl(fruitDao);
    }

    @Test
    public void makeReport_emptyStorage_Ok() {
        expected = new ArrayList<>();
        actual = reportMakerService.makeReport();
        assertEquals(expected, actual);
    }

    @Test
    public void makeReport_storageWithFruits_Ok() {
        expected = List.of("orange,13" + System.lineSeparator(),
                "apricot,67" + System.lineSeparator());
        Storage.fruits.put(new Fruit("orange"), 13);
        Storage.fruits.put(new Fruit("apricot"), 67);
        actual = reportMakerService.makeReport();
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}

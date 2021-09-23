package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static Fruit fruit;
    private static Fruit fruit1;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        fruit = new Fruit("banana");
        fruit1 = new Fruit("apple");
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put(fruit, 100);
        Storage.storage.put(fruit1, 200);
    }

    @Test
    public void getReport_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,200"
                + System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}

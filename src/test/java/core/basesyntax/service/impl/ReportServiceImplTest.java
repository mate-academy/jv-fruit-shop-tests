package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String TITLE = "fruit,quantity" + LINE_SEPARATOR;
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void init() {
        ShopStorage.fruitsStorage.put("banana", 100);
        ShopStorage.fruitsStorage.put("apple", 120);
        ShopStorage.fruitsStorage.put("avocado", 46);
        ShopStorage.fruitsStorage.put("tomato", 40);
    }

    @Test
    public void getReport_isOk() {
        String expected = TITLE
                + "banana,100" + LINE_SEPARATOR
                + "apple,120" + LINE_SEPARATOR
                + "avocado,46" + LINE_SEPARATOR
                + "tomato,40" + LINE_SEPARATOR;
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_nullStorage_notOk() {
        ShopStorage.fruitsStorage.clear();
        reportService.getReport();
    }

    @After
    public void clear() {
        ShopStorage.fruitsStorage.clear();
    }
}

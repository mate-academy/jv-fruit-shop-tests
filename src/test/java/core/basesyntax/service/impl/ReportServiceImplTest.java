package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final Map<Fruit, Integer> STORAGE = Storage.fruits;
    private static final String LINE_END = System.lineSeparator();
    private static final String NAME_OF_COLUMNS = "fruit,quantity" + LINE_END;
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_validData_ok() {
        STORAGE.put(new Fruit("banana"), 50);
        STORAGE.put(new Fruit("peach"), 35);
        STORAGE.put(new Fruit("melon"), 15);
        String expexcted = NAME_OF_COLUMNS
                + "banana,50" + LINE_END
                + "peach,35" + LINE_END
                + "melon,15" + LINE_END;
        String actual = reportService.createReport();
        assertEquals(expexcted, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_storageIsNull_notOk() {
        reportService.createReport();
    }

    @After
    public void tearDown() {
        STORAGE.clear();
    }
}

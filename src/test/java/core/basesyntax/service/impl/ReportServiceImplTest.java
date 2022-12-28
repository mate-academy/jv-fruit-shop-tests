package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.Assert.assertEquals;
import core.basesyntax.service.ReportService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String EXPECTED_REPORT =
            "fruit,quantity" + "\n"
            + "orange,10" + "\n"
            + "apple,15";
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        fruitStorage.put("apple", 15);
        fruitStorage.put("orange", 10);
    }

    @Test
    public void createReport_Ok() {
        String actual = reportService.createReport();
        assertEquals(EXPECTED_REPORT, actual);
    }

    @AfterClass
    public static void afterClass() {
        fruitStorage.clear();
    }
}

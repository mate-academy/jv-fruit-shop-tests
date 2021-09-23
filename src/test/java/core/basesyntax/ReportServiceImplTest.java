package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static final String REPORT_CAPTION = "fruit,quantity";
    private static ReportServiceImpl reportService;

    @BeforeAll
    public static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getReport_correctData_ok() {
        Storage.reportMap.put(new Fruit("apple"), 10);
        Storage.reportMap.put(new Fruit("banana"), 10);
        String expected = REPORT_CAPTION + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();
        String actual = reportService.getReport();
        Assertions.assertEquals(expected,actual);
    }
}


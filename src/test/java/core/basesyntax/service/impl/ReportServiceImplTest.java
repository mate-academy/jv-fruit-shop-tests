package core.basesyntax.service.impl;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static Map<Fruit, Integer> validRecords;
    private static ReportServiceImpl reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
        validRecords = new HashMap<>();
    }

    @Test
    public void makeReport_validData_Ok() {
        for (int i = 1; i <= 3; i++) {
            validRecords.put(new Fruit("F" + i), i);
        }
        assertThat(reportService.makeReport(validRecords),
                hasItems("fruit,quantity", "F1,1", "F2,2", "F3,3"));
    }

    @Test(expected = NullPointerException.class)
    public void makeReport_nullList_NotOk() {
        reportService.makeReport(null);
    }
}

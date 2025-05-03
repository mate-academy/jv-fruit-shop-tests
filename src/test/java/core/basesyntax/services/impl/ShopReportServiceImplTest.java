package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.services.ShopReportService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopReportServiceImplTest {
    private static ShopReportService reportService;
    private static String valueSeparator;
    private static String titleRow;
    private static Map<String, Integer> processedData;

    @BeforeClass
    public static void beforeClass() {
        valueSeparator = ",";
        titleRow = "fruit,quantity";
        processedData = Map.of("apple", 20, "banana", 100, "watermelon", 4);
    }

    @Before
    public void setUp() {
        reportService = new ShopReportServiceImpl();
    }

    @Test
    public void generateReport_validData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add(titleRow);
        processedData.forEach((k, v) -> expected.add(k + valueSeparator + v));
        assertEquals(expected, reportService.generateReport(processedData));
    }

    @Test
    public void generateReport_invalidData_ok() {
        List<String> expectedEmpty = new ArrayList<>();
        expectedEmpty.add(titleRow);
        assertEquals(expectedEmpty, reportService.generateReport(new HashMap<>()));
    }

    @Test(expected = NullDataException.class)
    public void generateReport_invalidData_notOk() {
        reportService.generateReport(null);
    }
}

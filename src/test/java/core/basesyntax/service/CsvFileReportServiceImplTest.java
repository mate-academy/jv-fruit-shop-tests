package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReportServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CsvFileReportServiceImplTest {
    private static CsvFileReportService csvFileReportService;
    private static final String FRUIT = "banana";
    private static final Integer QUANTITY = 10;
    private static final String EXPECTED_REPORT = "fruit,quantity\nbanana,10";
    private static final String EXPECTED_FIRST_LINE = "fruit,quantity";

    @BeforeAll
    public static void init() {
        csvFileReportService = new CsvFileReportServiceImpl();
    }

    @Test
    public void createReport_ReportWithOneProduct_Ok() {
        Map<String, Integer> products = new HashMap<>();
        products.put(FRUIT, QUANTITY);
        Assertions.assertEquals(EXPECTED_REPORT,
                csvFileReportService.createReport(products),
                "Incorrect report for a single product");
    }

    @Test
    public void createReport_ReportWithMultipleProducts_Ok() {
        Map<String, Integer> products = new HashMap<>();
        products.put("banana", 20);
        products.put("apple", 100);
        Assertions.assertEquals("fruit,quantity\nbanana,20\napple,100",
                csvFileReportService.createReport(products),
                "Incorrect report for multiple products");
    }

    @Test
    public void createReport_ReportWithEmptyProductList_Ok() {
        Map<String, Integer> products = new HashMap<>();
        Assertions.assertEquals(EXPECTED_FIRST_LINE,
                csvFileReportService.createReport(products),
                "Incorrect report for empty product list");
    }

    @Test
    public void createReport_NoProducts_NotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> csvFileReportService.createReport(null),
                "NullPointerException for no products expected");
    }
}

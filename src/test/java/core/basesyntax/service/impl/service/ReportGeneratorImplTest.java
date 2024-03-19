package core.basesyntax.service.impl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String TITLE = "fruit,quantity";
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.setElements(new HashMap<>());
    }

    @AfterEach
    void clear() {
        Storage.setElements(new HashMap<>());
    }

    @Test
    void returnOnlyTitleWithEmptyStorage_createReport_ok() {
        String expectedReport = TITLE + System.lineSeparator();
        String actualReport = reportGenerator.createReport();
        assertEquals(expectedReport, actualReport,
                "Report should contain only the title when storage is empty.");
    }

    @Test
    void returnCorrectReportWithSingleItemInStorage_createReport_ok() {
        LinkedHashMap<String, Integer> products = new LinkedHashMap<>();
        products.put("banana", 20);
        Storage.setElements(products);
        String expectedReport = TITLE + System.lineSeparator()
                + "banana,20" + System.lineSeparator();
        String actualReport = reportGenerator.createReport();
        assertEquals(expectedReport, actualReport,
                "Report should correctly include single storage item.");
    }

    @Test
    void returnCorrectReportWithMultipleItemsInStorage_createReport_ok() {
        LinkedHashMap<String, Integer> products = new LinkedHashMap<>();
        products.put("banana", 20);
        products.put("apple", 15);
        Storage.setElements(products);
        String expectedReport = TITLE + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();
        String actualReport = reportGenerator.createReport();
        assertEquals(expectedReport, actualReport,
                "Report should correctly include all storage items.");
    }
}

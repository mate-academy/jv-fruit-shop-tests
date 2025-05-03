package core.basesyntax.service.impl;

import static junit.framework.TestCase.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportService = new ReportGeneratorImpl();
    }

    @Test
    public void reportGenerate_Ok() {
        Storage.fruitsStorage.put("banana", 152);
        Storage.fruitsStorage.put("apple", 90);
        String expectedResult = "fruit, quantity" + System.lineSeparator() + "banana, 152"
                + System.lineSeparator() + "apple, 90";
        String actual = reportService.generateReport();
        assertEquals(expectedResult, actual);
    }

    @Test
    public void reportGenerate_EmptyData_Ok() {
        String actual = reportService.generateReport();
        String expecteResult = "fruit, quantity";
        assertEquals(expecteResult, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}

package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportBuilderService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportBuilderServiceImplTest {
    private static final ReportBuilderService
            reportBuilderService = new ReportBuilderServiceImpl();

    @BeforeClass
    public static void setUp() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 57);
        Storage.fruits.put("grapes", 758);
    }

    @Test
    public void buildReport_validData_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,57" + System.lineSeparator()
                + "grapes,758";
        assertEquals("Invalid report", expected, reportBuilderService.buildReport());
    }

    @Test(expected = RuntimeException.class)
    public void buildReport_emptyStorage_notOk() {
        Storage.fruits.clear();
        reportBuilderService.buildReport();
    }

    @Test
    public void buildReport_nullValueInStorage_ok() {
        Storage.fruits.put(null, null);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,57" + System.lineSeparator()
                + "grapes,758";
        assertEquals("Invalid report", expected, reportBuilderService.buildReport());
    }
}

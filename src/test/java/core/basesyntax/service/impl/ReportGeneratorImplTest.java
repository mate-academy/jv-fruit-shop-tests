package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportMakerService;
    private static final String HEAD = "fruit,quantity";
    private static final int INDEX_OF_FIRST_LINE = 1;

    @BeforeClass
    public static void beforeClass() {
        reportMakerService = new ReportGeneratorImpl();
        Storage.getFruits().clear();
    }

    @Test
    public void createReport_fromEmptyStorage_Ok() {
        String expected = HEAD + System.lineSeparator();
        String actual = reportMakerService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_validData_Ok() {
        String expected = "banana,50";
        Storage.put("banana", 50);
        String[] lines = reportMakerService.createReport().split(System.lineSeparator());
        String actual = lines[INDEX_OF_FIRST_LINE];
        assertEquals(expected, actual);
    }
}

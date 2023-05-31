package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void createReport_isValid_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        Storage.mapFruits.put("banana", 152);
        Storage.mapFruits.put("apple", 90);
        String actual = reportCreatorService.createReport();
        boolean isEqual = actual.equals(expected);
        assertTrue(isEqual);
    }

    @Test
    public void createReport_withEmptyMap_ok() {
        Storage.mapFruits.clear();
        String expected = "fruit,quantity";
        String actual = reportCreatorService.createReport();
        boolean isEqual = actual.equals(expected);
        assertTrue(isEqual);
    }
}

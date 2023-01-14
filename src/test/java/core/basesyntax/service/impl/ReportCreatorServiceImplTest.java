package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorServiceImpl reportCreatorService;
    private static String expected1;
    private static String expected2;

    @BeforeClass
    public static void beforeClass() {
        reportCreatorService = new ReportCreatorServiceImpl();
        expected1 = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        expected2 = "fruit,quantity";
    }

    @Test
    public void createReport_isValid_Ok() {
        Storage.mapFruits.put("banana", 152);
        Storage.mapFruits.put("apple", 90);
        String actual = reportCreatorService.createReport();
        boolean isEqual = actual.equals(expected1);
        assertTrue(isEqual);
    }

    @Test
    public void createReport_withEmptyMap_Ok() {
        Storage.mapFruits.clear();
        String actual = reportCreatorService.createReport();
        boolean isEqual = actual.equals(expected2);
        assertTrue(isEqual);
    }
}

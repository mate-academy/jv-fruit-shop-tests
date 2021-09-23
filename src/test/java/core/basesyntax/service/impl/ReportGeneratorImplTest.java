package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static String expected =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,10" + System.lineSeparator()
                    + "apple,122" + System.lineSeparator()
                    + "orange,1122";
    private final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();

    @Before
    public void setUp() {
        Storage.storage.clear();
        Storage.storage.put(new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("apple"), 122);
        Storage.storage.put(new Fruit("orange"), 1122);
    }

    @Test
    public void createDataForReport_Ok() {
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createDataForReport_empty_storage_OK() {
        Storage.storage.clear();
        String actual = reportGenerator.generateReport();
        String expectedResult = "fruit,quantity";
        assertEquals(expectedResult, actual);
    }

    @Test (expected = NullPointerException.class)
    public void createDataForReport_invalidData_NotOk() {
        Storage.storage.clear();
        Storage.storage.put(null, null);
        String actual = reportGenerator.generateReport();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.storage.clear();
    }
}

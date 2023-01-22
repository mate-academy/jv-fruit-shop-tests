package core.basesyntax.service.implementations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorTest {
    private static final String REPORT_TEMPLATE = "fruit,quantity";
    private ReportCreator reportCreator;

    @Before
    public void setUp() {
        reportCreator = new ReportCreator();
        Storage.fruits.put("banana", 23);
        Storage.fruits.put("apple", 10);
    }

    @Test
    public void provideReport_ok() {
        String expectedString = REPORT_TEMPLATE + System.lineSeparator() + "banana,23"
                + System.lineSeparator() + "apple,10";
        String actualString = reportCreator.provideReport(Storage.fruits);
        assertEquals(expectedString, actualString);
    }

    @Test
    public void provideReport_emptyStorage_ok() {
        String actualString = reportCreator.provideReport(new HashMap<>());
        assertEquals(REPORT_TEMPLATE, actualString);
    }

    @Test (expected = NullPointerException.class)
    public void provideReport_nullStorage_notOk() {
        reportCreator.provideReport(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}

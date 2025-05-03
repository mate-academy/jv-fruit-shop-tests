package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void createReport_correctDataFromStorage_Ok() {
        Storage.fruitsQuantity.put("banana", 152);
        Storage.fruitsQuantity.put("apple", 90);
        String actual = reportCreator.createReport(Storage.fruitsQuantity);
        String expected = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .toString();
        assertEquals(expected,actual);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String actual = reportCreator.createReport(Storage.fruitsQuantity);
        String expected = new StringBuilder("fruit,quantity")
                .toString();
        assertEquals(expected,actual);
    }

    @After
    public void tearDown() {
        Storage.fruitsQuantity.clear();
    }
}

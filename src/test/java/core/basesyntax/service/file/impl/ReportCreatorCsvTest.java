package core.basesyntax.service.file.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.file.ReportCreator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorCsvTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportCreator = new ReportCreatorCsv();
    }

    @Before
    public void setUp() {
        Storage.storage.add(new Fruit("apple", 150));
        Storage.storage.add(new Fruit("coconut", 45));
    }

    @Test
    public void createReport_ReportFromStorage_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,150" + System.lineSeparator()
                + "coconut,45" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_EmptyStorage_Ok() {
        Storage.storage.clear();
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }
}

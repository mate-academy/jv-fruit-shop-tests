package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitReportService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReportServiceImplTest {
    private static final Fruit APPLE = new Fruit("apple");
    private static final Fruit BANANA = new Fruit("banana");
    private static final String TITLE = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static FruitReportService service;

    @BeforeClass
    public static void beforeClass() {
        service = new FruitReportServiceImpl();
        Storage.storage.clear();
        Storage.storage.put(BANANA, 40);
        Storage.storage.put(APPLE, 25);
    }

    @Test
    public void test_createReport_Ok() {
        StringBuilder reportExpected = new StringBuilder();
        String expected = reportExpected.append(TITLE)
                .append(System.lineSeparator()).append("banana")
                .append(SEPARATOR).append("40").append(System.lineSeparator())
                .append("apple").append(SEPARATOR).append("25")
                .append(System.lineSeparator()).toString();
        String actual = service.getReport();
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void erase() {
        Storage.storage.clear();
    }
}

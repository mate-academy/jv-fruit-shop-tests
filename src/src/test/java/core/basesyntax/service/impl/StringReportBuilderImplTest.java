package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.StringReportBuilder;
import org.junit.Test;

public class StringReportBuilderImplTest {
    private final StringReportBuilder reportBuilder = new StringReportBuilderImpl();

    @Test(expected = RuntimeException.class)
    public void createReportFromEmptyStorage_notOk() {
        reportBuilder.buildReport();
    }

    @Test
    public void createReportFromStorageWithData() {
        Storage.FRUIT_STORAGE.put(new Fruit("lemon"), 15);
        Storage.FRUIT_STORAGE.put(new Fruit("banana"), 23);
        Storage.FRUIT_STORAGE.put(new Fruit("pineapple"), 47);
        String expected = "fruit,quantity" + System.lineSeparator() + "lemon,15"
                + System.lineSeparator() + "banana,23"
                + System.lineSeparator() + "pineapple,47";
        String actual = reportBuilder.buildReport();
        assertEquals(expected, actual);
        Storage.FRUIT_STORAGE.clear();
    }
}

package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String SEPARATOR = System.lineSeparator();
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void setUpBeforeClass() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void create_emptyStorage_ok() {
        String expected = "";
        String actual = reportCreator.create();
        assertEquals(expected,actual);
    }

    @Test
    public void create_StorageWithEntries_ok() {
        String expected = "fruit,quantity" + SEPARATOR
                + "banana,100" + SEPARATOR + "apple,90";
        Storage.fruitStorage.put("banana", 100);
        Storage.fruitStorage.put("apple", 90);
        String actual = reportCreator.create();
        assertEquals(expected,actual);
        Storage.fruitStorage.clear();
    }
}

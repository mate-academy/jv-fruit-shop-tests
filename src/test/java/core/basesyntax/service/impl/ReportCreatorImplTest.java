package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void setUpBeforeClass() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void create_emptyStorage_ok() {
        String expected = "";
        String actual = reportCreator.create();
        assertEquals(expected, actual);
    }

    @Test
    public void create_storageWithEntries_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator() + "apple,90";
        Storage.fruitStorage.put("banana", 100);
        Storage.fruitStorage.put("apple", 90);
        String actual = reportCreator.create();
        assertEquals(expected, actual);
        Storage.fruitStorage.clear();
    }
}

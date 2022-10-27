package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.Test;

public class ReportCreatorImplTest {
    private final ReportCreator reportCreator = new ReportCreatorImpl();

    @Test
    public void createReport_normalData_ok() {
        Storage.fruits.put("banana", 115);
        Storage.fruits.put("apple", 110);
        StorageDao storageDao = new StorageDaoImpl();

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,115" + System.lineSeparator()
                + "apple,110" + System.lineSeparator();
        String actual = reportCreator.createReport(storageDao);
        assertEquals(expected, actual);
        Storage.fruits.clear();
    }
}

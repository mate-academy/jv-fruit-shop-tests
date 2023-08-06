package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private ReportCreator reportCreator = new ReportCreatorImpl(storageDao);

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void createReport_validDataInStorage_ok() {
        Storage.fruitStorage.put("banana", 135);
        Storage.fruitStorage.put("apple", 279);
        String actual = reportCreator.createReport();
        String header = "fruit,quantity";
        String delimiter = ",";
        String expected = new StringBuilder(header + System.lineSeparator())
                .append("banana").append(delimiter).append(135).append(System.lineSeparator())
                .append("apple").append(delimiter).append(279).append(System.lineSeparator())
                .toString();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyStorage_ok() {
        String actual = reportCreator.createReport();
        String expected = "The fruit storage is empty";
        assertEquals(expected, actual);
    }
}

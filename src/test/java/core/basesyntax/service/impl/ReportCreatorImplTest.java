package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDao;
import core.basesyntax.db.StorageDaoImpl;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static StorageDao storageDao;
    private static ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        reportCreator = new ReportCreatorImpl(storageDao);
    }

    @Test
    void createReport_defaultStorage_ok() {
        Storage.fruits.put("banana", 12);
        Storage.fruits.put("apple", 5);
        String actual = reportCreator.createReport();
        String expected = """
                fruit,quantity
                banana,12
                apple,5""";
        assertEquals(expected, actual);
    }

    @Test
    void createReport_defaultStorage_notOk() {
        Storage.fruits.put("banana", -12);
        Storage.fruits.put("apple", 5);
        assertThrows(RuntimeException.class, () -> reportCreator.createReport());
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}

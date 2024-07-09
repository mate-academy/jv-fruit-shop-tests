package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String FRUIT_NAME = "apple";
    private static final int FRUIT_QUANTITY = 11;
    private static final String REPORT_FROM_EMPTY_STORAGE = "fruit,quantity";
    private static final String REPORT_FROM_NON_EMPTY_STORAGE = "fruit,quantity"
            + System.lineSeparator() + "apple,11";

    private static StorageDao storageDao;
    private static ReportGenerator reportGenerator;
    private static final Storage STORAGE = new Storage();

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        reportGenerator = new ReportGeneratorImpl();
        STORAGE.getFruits().clear();
    }

    @Test
    void getReport_FromEmptyStorage_Ok() {
        String actual = reportGenerator.getReport();
        String expected = REPORT_FROM_EMPTY_STORAGE;
        assertEquals(expected, actual);
    }

    @Test
    void getReport_NonEmptyStorage_Ok() {
        storageDao.addFruit(FRUIT_NAME, FRUIT_QUANTITY);
        String actual = reportGenerator.getReport();
        String expected = REPORT_FROM_NON_EMPTY_STORAGE;
        assertEquals(expected, actual);
    }
}

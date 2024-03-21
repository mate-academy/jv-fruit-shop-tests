package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReportGeneratorTest {
    private static ReportGenerator generator;
    private static StorageDao storageDao;

    static {
        storageDao = new StorageDaoImpl();
        generator = new CsvReportGenerator(storageDao);
    }

    @BeforeEach
    void clearStorage() {
        storageDao.getStorageState().clear();
    }

    @Test
    void generateReport_allValidConditions_oK() {
        storageDao.putToInventory("potato", 44);
        storageDao.putToInventory("redCar", 33);
        storageDao.putToInventory("apple", 22);
        storageDao.putToInventory("cucumber", 11);

        String expected = "fruit,quantity" + (System.lineSeparator())
                + "apple,22" + (System.lineSeparator())
                + "redCar,33" + (System.lineSeparator())
                + "potato,44" + (System.lineSeparator())
                + "cucumber,11";
        String actual = generator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_randomDataInInventory_Ok() {
        for (int i = 0; i < 3; i++) {
            storageDao.putToInventory("fruit" + i, i);
        }
        String actual = generator.generateReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "fruit2," + 2 + System.lineSeparator()
                + "fruit1," + 1 + System.lineSeparator()
                + "fruit0," + 0;
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_emptyStorage_emptyStringReturn() {
        assertEquals("", generator.generateReport());
    }
}

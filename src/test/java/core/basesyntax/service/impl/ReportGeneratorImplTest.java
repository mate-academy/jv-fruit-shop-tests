package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private ReportGenerator reportGenerator = new ReportGeneratorImpl(storageDao);

    @BeforeAll
    public static void storageSetUp() {
        Storage.fruits.put("banana", 1);
        Storage.fruits.put("orange", 2);
        Storage.fruits.put("pineapple", 3);
    }

    @Test
    void generateReport() {
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,1"
                + System.lineSeparator() + "orange,2"
                + System.lineSeparator() + "pineapple,3";
        assertEquals(expected, reportGenerator.generateReport(),
                "Method generateReport wrote wrong information");
    }
}

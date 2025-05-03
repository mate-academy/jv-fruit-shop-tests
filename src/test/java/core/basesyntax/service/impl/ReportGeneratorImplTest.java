package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static FruitStorageDao fruitStorageDao;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        reportGenerator = new ReportGeneratorImpl(fruitStorageDao);
    }

    @Test
    void generateReport_checkReport_Ok() {
        Storage.storage.put("banana", 50);
        Storage.storage.put("orange", 30);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "orange,30" + System.lineSeparator();
        assertEquals(expectedReport, reportGenerator.generateReport());
    }
}

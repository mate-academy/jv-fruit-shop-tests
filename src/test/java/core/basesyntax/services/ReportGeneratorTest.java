package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private ReportGenerator reportGenerator;
    private Storage storage;
    private FruitsDao fruitsDao;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruitsDao = new FruitsDaoImpl(storage);
        reportGenerator = new ReportGenerator(fruitsDao);
    }

    @Test
    void createReport_inventoryWithFruits_ok() {
        fruitsDao.balance("apple", 50);
        fruitsDao.balance("banana", 30);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,30" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        assertEquals(expectedReport, reportGenerator.createReport());
    }
}

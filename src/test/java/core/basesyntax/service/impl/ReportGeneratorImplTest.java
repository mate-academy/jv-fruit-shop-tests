package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static Storage storage;
    private static FruitDao fruitDao;
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void init() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void reportGenerator_createReport_isOk() {
        storage.getFruits().put("banana",100);
        storage.getFruits().put("apple",80);
        String expected = "banana,100" + System.lineSeparator() + "apple,80";
        String actual = reportGenerator.createReport(fruitDao);
        assertEquals(expected, actual);
    }
}

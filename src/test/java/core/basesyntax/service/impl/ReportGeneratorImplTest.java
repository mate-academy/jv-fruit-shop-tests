package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private final Storage storage = new Storage();
    private final FruitDao fruitDao = new FruitDaoImpl(storage);
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void reportGenerator_createReport_isOk() {
        storage.getFruits().put("banana",100);
        storage.getFruits().put("apple",80);
        String expected = "banana,100" + System.lineSeparator() + "apple,80";
        String actual = reportGenerator.createReport(fruitDao);
        assertEquals(expected, actual);
    }
}

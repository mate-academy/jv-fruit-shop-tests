package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private FruitDao fruitDao;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void generateReport_Ok() {
        String expected = "apple,110";
        Storage.storage.put("apple", 110);
        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl(fruitDao);
        String actual = reportGenerator.generateReport();

        assertEquals(expected, actual);
    }
}

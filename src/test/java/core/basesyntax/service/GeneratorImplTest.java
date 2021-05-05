package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GeneratorImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90";
    private Generator generator;
    private FruitDao fruitDao;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        generator = new GeneratorImpl(fruitDao);
    }

    @Test
    public void generateReport_generateReportFromValidStorage_Ok() {
        Storage.getFruits().put("banana", 152);
        Storage.getFruits().put("apple", 90);
        assertEquals(EXPECTED_REPORT, generator.generateReport());
    }

    @Test
    public void generateReport_generateReportFromEmptyStorage_Ok() {
        assertEquals("fruit,quantity", generator.generateReport());
    }

    @After
    public void tearDown() {
        fruitDao.clearStorage();
    }
}

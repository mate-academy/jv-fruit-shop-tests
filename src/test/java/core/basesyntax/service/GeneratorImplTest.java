package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneratorImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static Generator generator;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        generator = new GeneratorImpl(fruitDao);
    }

    @Test
    public void generateReport_FromValidStorage_Ok() {
        Storage.getFruits().put("banana", 152);
        Storage.getFruits().put("apple", 90);
        assertEquals(EXPECTED_REPORT, generator.generateReport());
    }

    @Test
    public void generateReport_FromEmptyStorage_Ok() {
        assertEquals("fruit,quantity" + System.lineSeparator(), generator.generateReport());
    }

    @After
    public void tearDown() {
        fruitDao.clearStorage();
    }
}

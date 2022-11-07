package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTests {
    private static ReportGenerator reportGenerator;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void generateReport_normalData_ok() {
        Storage.fruits.put(new Fruit("banana"), 115);
        Storage.fruits.put(new Fruit("apple"), 110);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,115" + System.lineSeparator()
                + "apple,110";
        String actual = reportGenerator.generateReport(fruitDao);
        assertEquals(expected, actual);
        Storage.fruits.clear();
    }

    @Test
    public void generateReport_emptyStorage_ok() {
        String expected = "fruit,quantity";
        String actual = reportGenerator.generateReport(fruitDao);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}

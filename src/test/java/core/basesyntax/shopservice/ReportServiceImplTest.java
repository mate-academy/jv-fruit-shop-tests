package core.basesyntax.shopservice;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dbimitation.Storage;
import core.basesyntax.fruitsassortment.Fruit;
import core.basesyntax.shopdao.FruitDaoImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String expected = "fruit,amount" + System.lineSeparator()
            + "banana," + 15 + System.lineSeparator()
            + "apple," + 35 + System.lineSeparator() + "mango," + 10;

    @BeforeClass
    public static void beforeClass() {
        Storage.getFruits().clear();
        Storage.getFruits().put(new Fruit("banana"), 15);
        Storage.getFruits().put(new Fruit("apple"), 35);
        Storage.getFruits().put(new Fruit("mango"), 10);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }

    @Test
    public void getReport() {
        assertEquals(expected, new ReportServiceImpl(new FruitDaoImpl()).getReport());
    }
}

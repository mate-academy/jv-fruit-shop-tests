package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String SEPARATOR = System.lineSeparator();
    private FruitService fruitService = new FruitServiceImpl();

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void createReportWithGoodData_Ok() {
        Storage.storage.put(new Fruit("coconut"), 10);
        Storage.storage.put(new Fruit("pamella"), 20);
        Storage.storage.put(new Fruit("anakonda"), 0);
        Storage.storage.put(new Fruit("kartofel"), Integer.MAX_VALUE);
        String expected = "fruit,quantity" + SEPARATOR
                + "pamella,20" + SEPARATOR
                + "kartofel," + Integer.MAX_VALUE + SEPARATOR
                + "coconut,10" + SEPARATOR
                + "anakonda,0";
        String actual = fruitService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReportWithBadData_Ok() {
        Storage.storage.put(new Fruit("◙Ў╚тh6→"), Integer.MIN_VALUE);
        Storage.storage.put(new Fruit(""), -100000);
        Storage.storage.put(new Fruit(null), 939023);
        Storage.storage.put(new Fruit("картоха123"), Integer.MAX_VALUE);
        String expected = "fruit,quantity" + SEPARATOR
                + "картоха123," + Integer.MAX_VALUE + SEPARATOR
                + "◙Ў╚тh6→," + Integer.MIN_VALUE + SEPARATOR
                + ",-100000" + SEPARATOR
                + "null,939023";
        String actual = fruitService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReportWithEmptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = fruitService.createReport();
        Assert.assertEquals(expected, actual);
    }
}

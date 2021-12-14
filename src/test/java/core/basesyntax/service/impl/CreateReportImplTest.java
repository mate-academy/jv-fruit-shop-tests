package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Test;

public class CreateReportImplTest {
    private ReportCreator reportCreator = new CreateReportImpl();

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void createFirstLineReportChecking() {
        String checkingString = reportCreator.reportCreator();
        assertEquals("fruit,quantity\n",checkingString);
    }

    @Test
    public void createReportChecking() {
        Fruit fruit = new Fruit();
        fruit.setNameFruit("pineapple");
        fruit.setQuantityFruit(1900);
        Storage.fruits.add(fruit);
        String checkingString2 = reportCreator.reportCreator();
        assertEquals("fruit,quantity\npineapple,1900\n",checkingString2);
    }
}

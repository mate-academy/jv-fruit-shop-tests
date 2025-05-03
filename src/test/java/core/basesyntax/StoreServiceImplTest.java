package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.StoreService;
import core.basesyntax.service.StoreServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class StoreServiceImplTest {
    private static final StoreService storeService = new StoreServiceImpl();
    private static final Fruit testFruit = new Fruit("banana");
    private static final Fruit testFruit1 = new Fruit("apple");

    @Test
    public void getReportValidInput_oK() {
        Storage.fruits.put(testFruit, 1);
        Storage.fruits.put(testFruit1, 2);
        String testReport = "fruit, quantity" + System.lineSeparator()
                + "banana,1" + System.lineSeparator()
                + "apple,2";
        String report = storeService.getReport();
        System.out.println(report);
        Assert.assertEquals(testReport, report);
    }
}

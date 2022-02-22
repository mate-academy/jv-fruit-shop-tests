package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.DataHandler;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ReportCreateService;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void setUp() {
        ReportCreateService reportCreateService = new FruitReportCreateService();
        DataHandler dataHandler = new FruitDataHandler(new OperationStrategyImpl());
        fruitShopService = new FruitShopServiceImpl(dataHandler, reportCreateService);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void makeDailyReport_ok() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,20");
        data.add("b,apple,100");
        data.add("s,banana,100");
        data.add("p,banana,13");
        data.add("r,apple,10");
        data.add("p,apple,20");
        data.add("p,banana,5");
        data.add("s,banana,50");

        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
        String actual = fruitShopService.makeDailyReport(data);
        assertEquals(expected, actual);
    }

    @Test
    public void makeDailyReport_emptyList() {
        List<String> data = new ArrayList<>();
        String expected = "fruit,quantity";
        String actual = fruitShopService.makeDailyReport(data);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void makeDailyReport_null() {
        fruitShopService.makeDailyReport(null);
    }
}

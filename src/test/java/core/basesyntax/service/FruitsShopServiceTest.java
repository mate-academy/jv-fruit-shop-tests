package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import core.basesyntax.database.FruitsStorage;
import core.basesyntax.service.activity.ActivityHandler;
import core.basesyntax.service.activity.BalanceHandler;
import core.basesyntax.service.activity.PurchaseHandler;
import core.basesyntax.service.activity.ReturnHandler;
import core.basesyntax.service.activity.SupplyHandler;
import core.basesyntax.service.minorservices.GenerateReportService;
import core.basesyntax.service.minorservices.GenerateReportServiceImpl;
import core.basesyntax.service.minorservices.ReaderService;
import core.basesyntax.service.minorservices.ReaderServiceImpl;
import core.basesyntax.service.strategy.ActivityStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitsShopServiceTest {
    private static FruitsShopService fruitsShopService;

    @BeforeClass
    public static void beforeClass() {
        Map<String, ActivityHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put("b", new BalanceHandler());
        activityHandlerMap.put("p", new PurchaseHandler());
        activityHandlerMap.put("s", new SupplyHandler());
        activityHandlerMap.put("r", new ReturnHandler());
        List<String> readerList = new ArrayList<>();
        readerList.add("type,fruit,quantity");
        readerList.add("b,banana,20");
        readerList.add("s,apple,100");
        readerList.add("r,apple,30");
        readerList.add("p,apple,10");
        ReaderService readerService = mock(ReaderServiceImpl.class);
        when(readerService.readFromFile("file.txt")).thenReturn(readerList);
        Set<String> generateReportList = new HashSet<>();
        generateReportList.add("banana");
        generateReportList.add("apple");
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100";
        GenerateReportService generateReportService = mock(GenerateReportServiceImpl.class);
        when(generateReportService.generateReport(generateReportList))
                .thenReturn(expected);
        fruitsShopService = new FruitsShopServiceImpl(
                new ActivityStrategyImpl(activityHandlerMap),
                readerService, generateReportService);
    }

    @Test
    public void createReport_validValue_Ok() {
        String filePath = "file.txt";
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100";
        assertEquals(expected, fruitsShopService.createReport(filePath));
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.fruitsStorage.clear();
    }
}

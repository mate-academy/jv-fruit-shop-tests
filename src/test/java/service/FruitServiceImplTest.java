package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitRecord;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.activity.ActivityHandler;
import service.activity.BalanceHandler;
import service.activity.PurchaseHandler;
import service.activity.SupplyHandler;

public class FruitServiceImplTest {
    private static ActivityStrategy activityStrategy;
    private static FruitService fruitService;
    private static List<FruitRecord> fruitRecordList;
    private static Map<String, Integer> expectedReport;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        Map<String, ActivityHandler> activityHandlersMap = new HashMap<>();
        activityHandlersMap.put("b", new BalanceHandler());
        activityHandlersMap.put("p", new PurchaseHandler());
        SupplyHandler supplyHandler = new SupplyHandler();
        activityHandlersMap.put("s", supplyHandler);
        activityHandlersMap.put("r", supplyHandler);
        activityStrategy = new ActivityStrategyImpl(activityHandlersMap);
        fruitService = new FruitServiceImpl();
        fruitRecordList = new ArrayList<>();
        expectedReport = new HashMap<>();
    }

    @After
    public void tearDown() {
        fruitRecordList.clear();
        expectedReport.clear();
    }

    @Test
    public void getReport_Ok() {
        fruitRecordList.add(new FruitRecord("b", "banana", 100));
        fruitRecordList.add(new FruitRecord("b", "apple", 50));
        fruitRecordList.add(new FruitRecord("p", "banana", 10));
        fruitRecordList.add(new FruitRecord("p", "apple", 5));
        fruitRecordList.add(new FruitRecord("s", "banana", 50));
        fruitRecordList.add(new FruitRecord("s", "apple", 10));
        fruitRecordList.add(new FruitRecord("r", "banana", 2));
        fruitRecordList.add(new FruitRecord("r", "apple", 1));
        expectedReport.put("banana", 142);
        expectedReport.put("apple", 56);
        Map<String, Integer> actualReport =
                fruitService.getReport(fruitRecordList, activityStrategy);
        Assert.assertEquals(expectedReport, actualReport);
    }

    @Test
    public void incorrectActivity_NotOk() {
        fruitRecordList.add(new FruitRecord("b", "banana", 100));
        fruitRecordList.add(new FruitRecord("b", "apple", 50));
        fruitRecordList.add(new FruitRecord("p", "banana", 10));
        fruitRecordList.add(new FruitRecord("p", "apple", 5));
        fruitRecordList.add(new FruitRecord("!", "banana", 50));
        fruitRecordList.add(new FruitRecord("s", "apple", 10));
        fruitRecordList.add(new FruitRecord("r", "banana", 2));
        fruitRecordList.add(new FruitRecord("r", "apple", 1));
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Incorrect activity: !");
        fruitService.getReport(fruitRecordList, activityStrategy);
    }

    @Test
    public void activityWithoutInitialBalance_NotOk() {
        fruitRecordList.add(new FruitRecord("b", "apple", 50));
        fruitRecordList.add(new FruitRecord("p", "banana", 10));
        fruitRecordList.add(new FruitRecord("p", "apple", 5));
        fruitRecordList.add(new FruitRecord("s", "banana", 50));
        fruitRecordList.add(new FruitRecord("s", "apple", 10));
        fruitRecordList.add(new FruitRecord("r", "banana", 2));
        fruitRecordList.add(new FruitRecord("r", "apple", 1));
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(
                "Activity without initial balance found for the fruit: banana");
        fruitService.getReport(fruitRecordList, activityStrategy);
    }

    @Test
    public void fruitListIsNull_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("FruitRecordList is null");
        fruitService.getReport(null, activityStrategy);
    }

    @Test
    public void activityStrategyIsNull_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("ActivityStrategy is null");
        fruitService.getReport(fruitRecordList, null);
    }

    @Test
    public void fruitListIsEmpty_Ok() {
        Map<String, Integer> actualReport =
                fruitService.getReport(fruitRecordList, activityStrategy);
        Assert.assertEquals(expectedReport, actualReport);
    }
}

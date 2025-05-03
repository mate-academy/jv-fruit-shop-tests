package core.basesyntax;

import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.model.FruitStorage;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.ReportCreatorImpl;
import core.basesyntax.service.amount.AdditionHandler;
import core.basesyntax.service.amount.AmountHandler;
import core.basesyntax.service.amount.SubtractionHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorTest {
    private static final FruitRecord.Type BALANCE = FruitRecord.Type.BALANCE;
    private static final FruitRecord.Type SUPPLY = FruitRecord.Type.SUPPLY;
    private static final FruitRecord.Type RETURN = FruitRecord.Type.RETURN;
    private static final FruitRecord.Type PURCHASE = FruitRecord.Type.PURCHASE;
    private static final List<FruitRecord> DATA_BASE = DataBase.DB;
    private static final Map<String, Integer> FRUIT_COUNT = FruitStorage.FRUIT_COUNT;
    private static Map<FruitRecord.Type, AmountHandler> strategies;
    private static List<FruitRecord> fruitRecords;
    private static List<String> reportRecords;
    private static OperationStrategy operationStrategy;
    private static AmountHandler additionHandler;
    private static AmountHandler subtractionHandler;

    @Before
    public void setUp() {
        additionHandler = new AdditionHandler();
        subtractionHandler = new SubtractionHandler();
        fruitRecords = new ArrayList<>();
        fruitRecords.add(new FruitRecord(20, BALANCE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(100, BALANCE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(100, SUPPLY, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(13, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(10, RETURN, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(20, PURCHASE, new Fruit("apple")));
        fruitRecords.add(new FruitRecord(5, PURCHASE, new Fruit("banana")));
        fruitRecords.add(new FruitRecord(50, SUPPLY, new Fruit("banana")));
        reportRecords = new ArrayList<>();
        reportRecords.add("banana,152");
        reportRecords.add("apple,90");
        strategies = new HashMap<>();
        strategies.put(BALANCE, additionHandler);
        strategies.put(SUPPLY, additionHandler);
        strategies.put(RETURN, additionHandler);
        strategies.put(PURCHASE, subtractionHandler);
        operationStrategy = new OperationStrategyImpl(strategies);
    }

    @Test
    public void createFruitRecordsReport_createReport_OK() {
        DATA_BASE.addAll(fruitRecords);
        ReportCreator reportCreator = new ReportCreatorImpl(operationStrategy);
        List<String> expected = reportRecords;
        List<String> actual = reportCreator.createReport();
        Assert.assertEquals("Test failed! Expected result differs from actual result!",
                expected, actual);
    }

    @Test
    public void createEmptyFruitRecordsReport_createReport_OK() {
        ReportCreator reportCreator = new ReportCreatorImpl(operationStrategy);
        List<String> expected = new ArrayList<>();
        List<String> actual = reportCreator.createReport();
        Assert.assertEquals("Test failed! Expected result differs from actual result!",
                expected, actual);
    }

    @After
    public void afterEachTest() {
        DATA_BASE.clear();
        FRUIT_COUNT.clear();
    }
}

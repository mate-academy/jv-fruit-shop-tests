package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.db.dao.FruitDao;
import core.basesyntax.db.dao.FruitDaoStorage;
import core.basesyntax.model.Activity;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitReporter;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.Validator;
import core.basesyntax.service.impl.FruitReporterImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ValidatorImpl;
import core.basesyntax.service.parsers.ActivityParser;
import core.basesyntax.service.parsers.FruitParser;
import core.basesyntax.service.parsers.impl.ActivityParserImpl;
import core.basesyntax.service.parsers.impl.FruitParserImpl;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.activities.ActivityService;
import core.basesyntax.strategy.activities.BalanceService;
import core.basesyntax.strategy.activities.PurchaseService;
import core.basesyntax.strategy.activities.ReturnService;
import core.basesyntax.strategy.activities.SupplyService;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String FILE_SEPARATOR = File.separator;
    private static final String RESOURCES_PATH = "src" + FILE_SEPARATOR
            + "main" + FILE_SEPARATOR
            + "resources" + FILE_SEPARATOR;
    private static final String FULL_CSV = RESOURCES_PATH
            + "FULL_CSV.csv";
    private static final String WRONG_CSV_PATH = RESOURCES_PATH
            + "WRONG_CSV_PATH.csv";
    private static final String WRONG_QUANTITY_CSV_PATH = RESOURCES_PATH
            + "WRONG_QUANTITY_CSV.csv";
    private static final String WRONG_VALUE_CSV_PATH = RESOURCES_PATH
            + "WRONG_VALUE_CSV.csv";
    private static final String WRONG_SUMMARY_VALUE_CSV_PATH = RESOURCES_PATH
            + "WRONG_SUMMARY_VALUE_CSV.csv";
    private static ActivityService balanceService;
    private static ActivityService purchaseService;
    private static ActivityService supplyService;
    private static ActivityService returnService;
    private static ActivityParser activityParser;
    private static ActivityStrategy activityStrategy;
    private static FruitDao fruitDao;
    private static FruitParser fruitParser;
    private static FruitReporter fruitReporter;
    private static FruitShopService fruitShopService;
    private static ReaderService readerService;
    private static Validator validator;
    private static List<Activity> expectedActivities;
    private static Activity balanceBananas20;
    private static Activity balanceApple100;
    private static Activity supplyBananas100;
    private static Activity purchaseBananas13;
    private static Activity returnApples10;
    private static Activity purchaseApples20;
    private static Activity purchaseBananas5;
    private static Activity supplyBananas50;
    private static int actualApplesQuantity;
    private static int expectedApplesQuantity;
    private static int expectedBananasQuantity;
    private static int actualBananasQuantity;

    @BeforeClass
    public static void setup() {
        activityParser = new ActivityParserImpl();
        activityStrategy = new ActivityStrategyImpl();
        balanceService = new BalanceService();
        expectedActivities = new ArrayList<>();
        fruitDao = new FruitDaoStorage();
        fruitParser = new FruitParserImpl();
        fruitReporter = new FruitReporterImpl();
        fruitShopService = new FruitShopServiceImpl();
        purchaseService = new PurchaseService();
        returnService = new ReturnService();
        readerService = new ReaderServiceImpl();
        supplyService = new SupplyService();
        validator = new ValidatorImpl();
        balanceBananas20 = new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.BANANA)
                .setQuantity(20)
                .build();
        balanceApple100 = new Activity.Builder()
                .setActivityType(ActivityType.BALANCE)
                .setFruit(Fruit.APPLE)
                .setQuantity(100)
                .build();
        supplyBananas100 = new Activity.Builder()
                .setActivityType(ActivityType.SUPPLY)
                .setFruit(Fruit.BANANA)
                .setQuantity(100)
                .build();
        purchaseBananas13 = new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.BANANA)
                .setQuantity(13)
                .build();
        returnApples10 = new Activity.Builder()
                .setActivityType(ActivityType.RETURN)
                .setFruit(Fruit.APPLE)
                .setQuantity(10)
                .build();
        purchaseApples20 = new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.APPLE)
                .setQuantity(20)
                .build();
        purchaseBananas5 = new Activity.Builder()
                .setActivityType(ActivityType.PURCHASE)
                .setFruit(Fruit.BANANA)
                .setQuantity(5)
                .build();
        supplyBananas50 = new Activity.Builder()
                .setActivityType(ActivityType.SUPPLY)
                .setFruit(Fruit.BANANA)
                .setQuantity(50)
                .build();
        expectedActivities.add(balanceBananas20);
        expectedActivities.add(balanceApple100);
        expectedActivities.add(supplyBananas100);
        expectedActivities.add(purchaseBananas13);
        expectedActivities.add(returnApples10);
        expectedActivities.add(purchaseApples20);
        expectedActivities.add(purchaseBananas5);
        expectedActivities.add(supplyBananas50);
    }

    @Before
    public void init() {
        Storage.fruitsStorage.remove(Fruit.APPLE);
        Storage.fruitsStorage.remove(Fruit.BANANA);
    }

    @Test
    public void readFile_WithCorrectPath_OK() {
        List<Activity> actualActivities = readerService.readFile(Path.of(FULL_CSV));
        assertEquals(expectedActivities, actualActivities);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_WithWrongPath_Not_OK() {
        readerService.readFile(Path.of(WRONG_CSV_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void readFile_WithNullPath_Not_OK() {
        readerService.readFile(Path.of(null));
    }

    @Test
    public void getReport_getReport_OK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,90" + LINE_SEPARATOR
                + "banana,152" + LINE_SEPARATOR;
        String actual = fruitShopService.getReport(FULL_CSV);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_getReportWithNull_NotOK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,90" + LINE_SEPARATOR
                + "banana,152" + LINE_SEPARATOR;
        String actual = fruitShopService.getReport(null);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithWrongQuantity_Not_OK() {
        fruitShopService.getReport(WRONG_QUANTITY_CSV_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithWrongValues_Not_OK() {
        fruitShopService.getReport(WRONG_VALUE_CSV_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void getReport_WithWrongSummaryValues_Not_OK() {
        fruitShopService.getReport(WRONG_SUMMARY_VALUE_CSV_PATH);
    }

    @Test
    public void read_getCorrectQuantityFromStorage_OK() {
        int actual = 5;
        fruitDao.create(Fruit.APPLE, actual);
        int expected = fruitDao.read(Fruit.APPLE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_getQuantityFromStorageForAbsentFruit_NotOK() {
        fruitDao.read(Fruit.BANANA);
    }

    @Test(expected = RuntimeException.class)
    public void read_getQuantityFromStorageForNull_NotOK() {
        fruitDao.read(null);
    }

    @Test
    public void getActivity_getServiceStrategy_OK() {
        assertEquals(BalanceService.class, new ActivityStrategyImpl()
                .getActivity(ActivityType.BALANCE)
                .getClass());
        assertEquals(PurchaseService.class, new ActivityStrategyImpl()
                .getActivity(ActivityType.PURCHASE)
                .getClass());
        assertEquals(ReturnService.class, new ActivityStrategyImpl()
                .getActivity(ActivityType.RETURN)
                .getClass());
        assertEquals(SupplyService.class, new ActivityStrategyImpl()
                .getActivity(ActivityType.SUPPLY)
                .getClass());
    }

    @Test(expected = RuntimeException.class)
    public void getActivity_getServiceStrategyWithNull_NotOK() {
        assertEquals(BalanceService.class, new ActivityStrategyImpl()
                .getActivity(null)
                .getClass());
    }

    @Test
    public void apply_checkResultsWithApplyAllServices_OK() {
        balanceService.apply(balanceBananas20);
        balanceService.apply(balanceApple100);
        expectedApplesQuantity = balanceApple100.getQuantity();
        actualApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expectedApplesQuantity, actualApplesQuantity);
        expectedApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE)
                - purchaseApples20.getQuantity();
        purchaseService.apply(purchaseApples20);
        actualApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expectedApplesQuantity, actualApplesQuantity);
        expectedApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE)
                + returnApples10.getQuantity();
        returnService.apply(returnApples10);
        actualApplesQuantity = Storage.fruitsStorage.get(Fruit.APPLE);
        assertEquals(expectedApplesQuantity, actualApplesQuantity);
        expectedBananasQuantity = Storage.fruitsStorage.get(Fruit.BANANA)
                + supplyBananas50.getQuantity();
        supplyService.apply(supplyBananas50);
        actualBananasQuantity = Storage.fruitsStorage.get(Fruit.BANANA);
        assertEquals(expectedBananasQuantity, actualBananasQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void apply_checkBalanceServiceWithNull_NotOK() {
        balanceService.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_checkPurchaseServiceWithNull_NotOK() {
        purchaseService.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_checkReturnServiceWithNull_NotOK() {
        returnService.apply(null);
    }

    @Test(expected = RuntimeException.class)
    public void apply_checkSupplyServiceWithNull_NotOK() {
        supplyService.apply(null);
    }

    @Test
    public void validate_checkValidatorWithProperValues_OK() {
        String[] values = new String[]{"b", "banana", "100"};
        assertTrue(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNegativeQuantity_NotOK() {
        String[] values = new String[]{"b", "banana", "-100"};
        assertFalse(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNull_NotOK() {
        assertTrue(validator.validate(null));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNullActivityIdentifier_NotOK() {
        String[] values = new String[]{null, "banana", "100"};
        assertTrue(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNullFruitName_NotOK() {
        String[] values = new String[]{"b", null, "100"};
        assertTrue(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithNullQuantity_NotOK() {
        String[] values = new String[]{"b", "banana", null};
        assertTrue(validator.validate(values));
    }

    @Test(expected = RuntimeException.class)
    public void validate_checkValidatorWithWrongFruitName_NotOK() {
        String[] values = new String[]{"b", "grape", "100"};
        assertTrue(validator.validate(values));
    }

    @Test
    public void parse_getActivityWithActivityParser_OK() {
        String values = "b,banana,20";
        assertEquals(balanceBananas20, activityParser.parse(values));
    }

    @Test(expected = RuntimeException.class)
    public void parse_getActivityWithActivityParserWithNull_NotOK() {
        assertEquals(balanceBananas20, activityParser.parse(null));
    }

    @Test(expected = RuntimeException.class)
    public void parse_ActivityParserWithSpaces_NotOK() {
        String values = "b, banana, 20";
        assertEquals(balanceBananas20, activityParser.parse(values));
    }

    @Test
    public void parse_getFruitWithFruitParser_OK() {
        assertEquals(fruitParser.parse("banana"), Fruit.BANANA);
    }

    @Test(expected = RuntimeException.class)
    public void parse_getFruitWithFruitParserWithNull_OK() {
        assertEquals(fruitParser.parse(null), Fruit.BANANA);
    }

    @Test
    public void report_withValuesInCorrectOrder_OK() {
        balanceService.apply(balanceApple100);
        balanceService.apply(balanceBananas20);
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,100" + LINE_SEPARATOR
                + "banana,20" + LINE_SEPARATOR;
        String actual = fruitReporter.report(Storage.fruitsStorage);
        assertEquals(expected, actual);
    }

    @Test
    public void report_withValuesInWrongOrder_OK() {
        balanceService.apply(balanceBananas20);
        balanceService.apply(balanceApple100);
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,100" + LINE_SEPARATOR
                + "banana,20" + LINE_SEPARATOR;
        String actual = fruitReporter.report(Storage.fruitsStorage);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void report_withNull_OK() {
        fruitReporter.report(null);
    }
}

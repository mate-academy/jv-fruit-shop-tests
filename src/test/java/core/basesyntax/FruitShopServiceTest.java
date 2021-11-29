package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.dao.FruitDao;
import core.basesyntax.db.dao.FruitDaoStorage;
import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import java.io.File;

import core.basesyntax.strategy.activities.BalanceService;
import core.basesyntax.strategy.activities.PurchaseService;
import core.basesyntax.strategy.activities.ReturnService;
import core.basesyntax.strategy.activities.SupplyService;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
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
    private FruitShopService fruitShopService;
    private FruitDao fruitDao;

    @Before
    public void init() {
        Storage.fruitsStorage.remove(Fruit.APPLE);
        Storage.fruitsStorage.remove(Fruit.BANANA);
        fruitShopService = new FruitShopServiceImpl();
        fruitDao = new FruitDaoStorage();
    }

    @Test
    public void getReport_CorrectReport_OK() {
        String expected = "fruit,quantity" + LINE_SEPARATOR
                + "apple,90" + LINE_SEPARATOR
                + "banana,152" + LINE_SEPARATOR;
        String actual = fruitShopService.getReport(FULL_CSV);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_WithWrongPath_Not_OK() {
        fruitShopService.getReport(WRONG_CSV_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFile_WithWrongValues_Not_OK() {
        fruitShopService.getReport(WRONG_VALUE_CSV_PATH);
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
    public void read_getSameQuantityFromStorage_OK() {
        int actual = 5;
        fruitDao.create(Fruit.APPLE,actual);
        int expected = fruitDao.read(Fruit.APPLE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_getValueFromAbsentFruitStorage_NotOK() {
        fruitDao.read(Fruit.BANANA);
        System.out.println(fruitDao.read(Fruit.BANANA));
    }

    @Test
    public void getActivity_getServiceStrategyWithProperEnums_OK() {
        assertEquals(BalanceService.class, new ActivityStrategyImpl().getActivity(ActivityType.BALANCE).getClass());
        assertEquals(PurchaseService.class, new ActivityStrategyImpl().getActivity(ActivityType.PURCHASE).getClass());
        assertEquals(ReturnService.class, new ActivityStrategyImpl().getActivity(ActivityType.RETURN).getClass());
        assertEquals(SupplyService.class, new ActivityStrategyImpl().getActivity(ActivityType.SUPPLY).getClass());
    }

    @Test
    public void apply_checkResultsWithApplyActivity_OK() {

    }

    @Test
    public void validate_checkValidatorWithProperValues_OK() {

    }

    @Test
    public void report() {

    }

    @Test
    public void parse_() {

    }
}

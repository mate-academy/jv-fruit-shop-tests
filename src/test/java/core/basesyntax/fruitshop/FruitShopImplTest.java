package core.basesyntax.fruitshop;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ActivitiesStrategy;
import core.basesyntax.service.ActivitiesStrategyImpl;
import core.basesyntax.service.activities.ActivityHandler;
import core.basesyntax.service.activities.BalanceHandler;
import core.basesyntax.service.activities.PurchaseHandler;
import core.basesyntax.service.activities.ReturnHandler;
import core.basesyntax.service.activities.SupplyHandler;
import core.basesyntax.service.activities.TypeOfActivities;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitShopImplTest {
    private static FruitShop fruitShop;
    private static ActivitiesStrategy strategy;
    private static Map<TypeOfActivities, ActivityHandler> typeOfActivitiesMap;
    private static final String FILE_PATH_NORMAL_DATA = "src/main/resources/inputData.csv";
    private static final String EMPTY_FILE = "src/main/resources/empty.csv";
    private static final String INCORRECT_TYPE_OF_ACTIVITIES =
            "src/main/resources/incorrectInputData1.csv";
    private static final String FAIL_DATA = "src/main/resources/incorrectInputData2.csv";
    private static final String DEFICIENCY_DATA = "src/main/resources/incorrectInputData3.csv";
    private static final String REPORT_PATH = "src/main/resources/report.csv";
    private String expected;

    @Before
    public void setUp() {
        typeOfActivitiesMap = new HashMap<>();
        typeOfActivitiesMap.put(TypeOfActivities.BALANCE, new BalanceHandler());
        typeOfActivitiesMap.put(TypeOfActivities.SUPPLY, new SupplyHandler());
        typeOfActivitiesMap.put(TypeOfActivities.PURCHASE, new PurchaseHandler());
        typeOfActivitiesMap.put(TypeOfActivities.RETURN, new ReturnHandler());
        strategy = new ActivitiesStrategyImpl(typeOfActivitiesMap);
        Storage.FRUITS_QUANTITY.clear();
        Storage.RECORDS.clear();
    }

    @Test
    public void fruitShop_normalData_Ok() {
        fruitShop = new FruitShopImpl(strategy, FILE_PATH_NORMAL_DATA, REPORT_PATH);
        expected = "Report created!";
        assertEquals("Can't create report at this file "
                + FILE_PATH_NORMAL_DATA, expected, fruitShop.createNewReport());
    }

    @Test(expected = RuntimeException.class)
    public void fruitShop_emptyFileData_Ok() {
        fruitShop = new FruitShopImpl(strategy, EMPTY_FILE, REPORT_PATH);
        fruitShop.createNewReport();
    }

    @Test(expected = RuntimeException.class)
    public void fruitShop_incorrectTypeOfActivities_Ok() {
        fruitShop = new FruitShopImpl(strategy, INCORRECT_TYPE_OF_ACTIVITIES, REPORT_PATH);
        fruitShop.createNewReport();
    }

    @Test(expected = RuntimeException.class)
    public void fruitShop_incorrectInputData_Ok() {
        fruitShop = new FruitShopImpl(strategy, FAIL_DATA, REPORT_PATH);
        fruitShop.createNewReport();
    }

    @Test(expected = RuntimeException.class)
    public void fruitShop_deficiencyData_Ok() {
        fruitShop = new FruitShopImpl(strategy, DEFICIENCY_DATA, REPORT_PATH);
        fruitShop.createNewReport();
    }
}

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
    private static final String filePathNormalData = "src/main/resources/inputData.csv";
    private static final String emptyFile = "src/main/resources/empty.csv";
    private static final String incorrectTypeOfActivities =
            "src/main/resources/incorrectInputData1.csv";
    private static final String failData = "src/main/resources/incorrectInputData2.csv";
    private static final String deficiencyData = "src/main/resources/incorrectInputData3.csv";
    private static final String reportPath = "src/main/resources/report.csv";
    private String expected;

    @Before
    public void setUp() {
        typeOfActivitiesMap = new HashMap<>();
        typeOfActivitiesMap.put(TypeOfActivities.BALANCE, new BalanceHandler());
        typeOfActivitiesMap.put(TypeOfActivities.SUPPLY, new SupplyHandler());
        typeOfActivitiesMap.put(TypeOfActivities.PURCHASE, new PurchaseHandler());
        typeOfActivitiesMap.put(TypeOfActivities.RETURN, new ReturnHandler());
        strategy = new ActivitiesStrategyImpl(typeOfActivitiesMap);
        Storage.fruitsQuantity.clear();
        Storage.records.clear();
    }

    @Test
    public void fruitShop_normalData_Ok() {
        fruitShop = new FruitShopImpl(strategy, filePathNormalData, reportPath);
        expected = "Report created!";
        assertEquals("Can't create report at this file "
                + filePathNormalData, expected, fruitShop.createNewReport());
    }

    @Test(expected = RuntimeException.class)
    public void fruitShop_emptyFileData_Ok() {
        fruitShop = new FruitShopImpl(strategy, emptyFile, reportPath);
        fruitShop.createNewReport();
    }

    @Test(expected = RuntimeException.class)
    public void fruitShop_incorrectTypeOfActivities_Ok() {
        fruitShop = new FruitShopImpl(strategy, incorrectTypeOfActivities, reportPath);
        fruitShop.createNewReport();
    }

    @Test(expected = RuntimeException.class)
    public void fruitShop_incorrectInputData_Ok() {
        fruitShop = new FruitShopImpl(strategy, failData, reportPath);
        fruitShop.createNewReport();
    }

    @Test(expected = RuntimeException.class)
    public void fruitShop_deficiencyData_Ok() {
        fruitShop = new FruitShopImpl(strategy, deficiencyData, reportPath);
        fruitShop.createNewReport();
    }
}

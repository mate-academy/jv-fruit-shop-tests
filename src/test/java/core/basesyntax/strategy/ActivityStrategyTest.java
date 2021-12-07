package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.FruitShopService;
import core.basesyntax.model.ActivityType;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.activities.BalanceService;
import core.basesyntax.strategy.activities.PurchaseService;
import core.basesyntax.strategy.activities.ReturnService;
import core.basesyntax.strategy.activities.SupplyService;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
import java.io.File;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityStrategyTest {
    private static FruitShopService fruitShopService;
    private static ActivityStrategy activityStrategy;
    private static final String FILE_SEPARATOR = File.separator;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String RESOURCES_PATH = "src" + FILE_SEPARATOR
            + "main" + FILE_SEPARATOR
            + "resources" + FILE_SEPARATOR;
    private static final String FULL_CSV = RESOURCES_PATH
            + "CSV_WITH_ORIGINAL_VALUES.csv";
    private static final String WRONG_CSV_PATH = RESOURCES_PATH
            + "WRONG_CSV_PATH.csv";
    private static final String WRONG_QUANTITY_CSV_PATH = RESOURCES_PATH
            + "WRONG_QUANTITY_CSV.csv";
    private static final String WRONG_VALUE_CSV_PATH = RESOURCES_PATH
            + "WRONG_VALUE_CSV.csv";
    private static final String WRONG_SUMMARY_VALUE_CSV_PATH = RESOURCES_PATH
            + "CSV_WITH_WRONG_SUMMARY_VALUE.csv";

    @BeforeClass
    public static void setup() {
        activityStrategy = new ActivityStrategyImpl();
        fruitShopService = new FruitShopServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void getActivity_getServiceStrategyWithNull_NotOK() {
        assertEquals(BalanceService.class, new ActivityStrategyImpl()
                .getActivity(null)
                .getClass());
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
}

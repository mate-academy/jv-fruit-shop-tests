package service;

import static org.junit.Assert.assertEquals;

import dao.FruitDao;
import dao.FruitDaoImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import service.actions.ActivityHandler;
import service.actions.Balance;
import service.actions.Purchase;
import service.actions.SupplyOrReturn;
import service.util.DataValidator;
import service.util.DataValidatorImpl;

public class ActivityStrategyTest {
    private static FruitDao fruitDao;
    private static DataValidator dataValidator;
    private static ActivityStrategy activityStrategy;

    @BeforeClass
    public static void beforeClass() {
        dataValidator = new DataValidatorImpl();
        fruitDao = new FruitDaoImpl();
        Map<String, ActivityHandler> data = new HashMap<>();
        data.put("BalanceHandler", new Balance(fruitDao));
        data.put("PurchaseHandler", new Purchase(fruitDao, dataValidator));
        data.put("SupplyOrReturn", new SupplyOrReturn(fruitDao));
        activityStrategy = new ActivityStrategyImpl(data);
    }

    @Test
    public void get_putDataAndGetIt_OK() {
        ActivityHandler actual = activityStrategy.get("BalanceHandler");
        ActivityHandler expected = new Balance(fruitDao);
        assertEquals(actual.getClass(), expected.getClass());

        actual = activityStrategy.get("PurchaseHandler");
        expected = new Purchase(fruitDao, dataValidator);
        assertEquals(actual.getClass(), expected.getClass());

        actual = activityStrategy.get("SupplyOrReturn");
        expected = new SupplyOrReturn(fruitDao);
        assertEquals(actual.getClass(), expected.getClass());
    }
}

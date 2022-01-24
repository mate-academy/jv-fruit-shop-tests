package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.TypeActivity;
import core.basesyntax.strategy.impl.ActivitiesStrategyImpl;
import core.basesyntax.strategy.impl.BalanceActivitiesShop;
import core.basesyntax.strategy.impl.PurchaseActivitiesShop;
import core.basesyntax.strategy.impl.SupplyActivitiesShop;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivitiesStrategyTest {
    private static ActivitiesStrategy activitiesStrategy;
    private static Map<TypeActivity, ActivitiesShop> operationTest;
    private static List<FruitOperation> fruitInfo;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationTest = new HashMap<>();
        operationTest.put(TypeActivity.BALANCE, new BalanceActivitiesShop());
        operationTest.put(TypeActivity.PURCHASE, new PurchaseActivitiesShop());
        operationTest.put(TypeActivity.SUPPLY, new SupplyActivitiesShop());
        operationTest.put(TypeActivity.RETURN, new SupplyActivitiesShop());
        activitiesStrategy = new ActivitiesStrategyImpl(operationTest);
        fruitInfo = new ArrayList<>();
    }

    @Test
    public void getActivityBalance_Ok() {
        ActivitiesShop actual = new BalanceActivitiesShop();
        ActivitiesShop expect = operationTest.get(TypeActivity.BALANCE);
        assertEquals(expect.getClass().getSimpleName(), actual.getClass().getSimpleName());
    }

    @Test
    public void getActivityPurchase_Ok() {
        ActivitiesShop actual = new PurchaseActivitiesShop();
        ActivitiesShop expect = operationTest.get(TypeActivity.PURCHASE);
        assertEquals(expect.getClass().getSimpleName(), actual.getClass().getSimpleName());
    }

    @Test
    public void getActivitySupply_Ok() {
        ActivitiesShop actual = new SupplyActivitiesShop();
        ActivitiesShop expect = operationTest.get(TypeActivity.SUPPLY);
        assertEquals(expect.getClass().getSimpleName(), actual.getClass().getSimpleName());
    }

    @Test
    public void getTypeActivityReturn_Ok() {
        ActivitiesShop actual = new SupplyActivitiesShop();
        ActivitiesShop expect = operationTest.get(TypeActivity.RETURN);
        assertEquals(expect.getClass().getSimpleName(), actual.getClass().getSimpleName());
    }

    @Test
    public void activityStrategy_Ok() {
        fruitInfo.add(new FruitOperation(TypeActivity.BALANCE, new Fruit("apple"), 10));
        fruitInfo.add(new FruitOperation(TypeActivity.BALANCE, new Fruit("banana"), 30));
        Map<Fruit, Integer> expect = new HashMap<>();
        expect.put(new Fruit("apple"), 10);
        expect.put(new Fruit("banana"), 30);
        activitiesStrategy.operation(fruitInfo);
        assertEquals(expect.entrySet(), Storage.fruits.entrySet());
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}

package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.TypeActivity;
import core.basesyntax.strategy.impl.ActivitiesStrategyImpl;
import core.basesyntax.strategy.impl.BalanceActivitiesShop;
import core.basesyntax.strategy.impl.PurchaseActivitiesShop;
import core.basesyntax.strategy.impl.SupplyActivitiesShop;
import org.junit.Test;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

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
        String expect = BalanceActivitiesShop.class.getSimpleName();
        String actual = operationTest.get(TypeActivity.BALANCE).getClass().getSimpleName();
        assertEquals(expect, actual);
    }

    @Test
    public void getWrongActivityBalance_NotOk() {
        String expect = BalanceActivitiesShop.class.getSimpleName();
        String actual = operationTest.get(TypeActivity.PURCHASE).getClass().getSimpleName();
        assertNotEquals(expect, actual);
    }

    @Test
    public void getActivityPurchase_Ok() {
        String expect = PurchaseActivitiesShop.class.getSimpleName();
        String actual = operationTest.get(TypeActivity.PURCHASE).getClass().getSimpleName();
        assertEquals(expect, actual);
    }

    @Test
    public void getWrongActivityPurchase_NotOk() {
        String expect = PurchaseActivitiesShop.class.getSimpleName();
        String actual = operationTest.get(TypeActivity.BALANCE).getClass().getSimpleName();
        assertNotEquals(expect, actual);
    }

    @Test
    public void getActivitySupply_Ok() {
        String expect = SupplyActivitiesShop.class.getSimpleName();
        String actual = operationTest.get(TypeActivity.SUPPLY).getClass().getSimpleName();
        assertEquals(expect, actual);
    }

    @Test
    public void getWrongActivitySupply_NotOk() {
        String expect = SupplyActivitiesShop.class.getSimpleName();
        String actual = operationTest.get(TypeActivity.BALANCE).getClass().getSimpleName();
        assertNotEquals(expect, actual);
    }

    @Test
    public void getTypeActivityReturn_Ok() {
        String expect = SupplyActivitiesShop.class.getSimpleName();
        String actual = operationTest.get(TypeActivity.RETURN).getClass().getSimpleName();
        assertEquals(expect, actual);
    }

    @Test
    public void ActivityStrategy_Ok() {
        fruitInfo.add(new FruitOperation(TypeActivity.BALANCE, new Fruit("apple"), 10));
        fruitInfo.add(new FruitOperation(TypeActivity.BALANCE, new Fruit("banana"), 30));
        Map<Fruit, Integer> expect = new HashMap<>();
        expect.put(new Fruit("apple"), 10);
        expect.put(new Fruit("banana"), 30);
        activitiesStrategy.operation(fruitInfo);
        assertEquals(expect.entrySet(), Storage.fruits.entrySet());
    }

    @Test
    public void ActivityStrategy_NotOk() {
        fruitInfo.add(new FruitOperation(TypeActivity.BALANCE, new Fruit("apple"), 10));
        Map<Fruit, Integer> expect = new HashMap<>();
        expect.put(new Fruit("banana"), 30);
        activitiesStrategy.operation(fruitInfo);
        assertNotEquals(expect.entrySet(), Storage.fruits.entrySet());
    }

    @Test
    public void ActivityStrategyIsEmpty_NotOk() {
        fruitInfo.add(new FruitOperation(TypeActivity.BALANCE, new Fruit("apple"), 10));
        Map<Fruit, Integer> expect = new HashMap<>();
        activitiesStrategy.operation(fruitInfo);
        assertNotEquals(expect.entrySet(), Storage.fruits.entrySet());
    }
}

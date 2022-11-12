package myfirstproject.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import myfirstproject.dao.FruitDao;
import myfirstproject.dao.impl.FruitDaoImpl;
import myfirstproject.db.CustomDataBase;
import myfirstproject.model.Fruit;
import myfirstproject.model.Operation;
import myfirstproject.service.PreparingData;
import myfirstproject.strategy.BalanceOperation;
import myfirstproject.strategy.OperationHandler;
import myfirstproject.strategy.PurchaseOperation;
import myfirstproject.strategy.ReturnOperation;
import myfirstproject.strategy.SeparationOfOperations;
import myfirstproject.strategy.SupplyOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationsTests {
    private static final FruitDao FRUIT_DAO = new FruitDaoImpl();
    private static final SeparationOfOperations SEPARATION = new SeparationOfOperations();
    private static final List<String[]> TEMPORAL_LIST = new ArrayList<>();
    private static final OperationHandler BALANCE_OPERATION = new BalanceOperation();
    private static final OperationHandler PURCHASE_OPERATION = new PurchaseOperation();
    private static final OperationHandler RETURN_OPERATION = new ReturnOperation();
    private static final OperationHandler SUPPLY_OPERATION = new SupplyOperation();
    private static Map<String, OperationHandler> operation;
    private final Fruit fruitApple = new Fruit("apple");
    private final Fruit fruitBanana = new Fruit("banana");
    private final int value = 10;
    private Map<Fruit, Integer> expected;

    @BeforeClass
    public static void before() {
        operation = new HashMap<>();
    }

    @Test
    public void testInitializedOperations_ok() {
        operation.put(Operation.BALANCE.getOperation(), BALANCE_OPERATION);
        operation.put(Operation.SUPPLY.getOperation(), SUPPLY_OPERATION);
        operation.put(Operation.PURCHASE.getOperation(), PURCHASE_OPERATION);
        operation.put(Operation.RETURN.getOperation(), RETURN_OPERATION);
        SEPARATION.toDoEachOperation(FRUIT_DAO, TEMPORAL_LIST, operation);
        Assert.assertEquals(Operation.BALANCE.getOperation(), "b");
        Assert.assertEquals(Operation.SUPPLY.getOperation(), "s");
        Assert.assertEquals(Operation.PURCHASE.getOperation(), "p");
        Assert.assertEquals(Operation.RETURN.getOperation(), "r");
    }

    @Test
    public void testBalance_ok() {
        expected = new HashMap<>();
        expected.put(fruitApple, value);
        BALANCE_OPERATION.changeValue(FRUIT_DAO, fruitApple, value);
        Assert.assertEquals(expected, CustomDataBase.storage);
    }

    @Test
    public void testSupply_ok() {
        expected = new HashMap<>();
        expected.put(fruitApple, value + value);
        CustomDataBase.storage.put(fruitApple, value);
        SUPPLY_OPERATION.changeValue(FRUIT_DAO, fruitApple, value);
        System.out.println(expected);
        System.out.println(CustomDataBase.storage);
        Assert.assertEquals(expected, CustomDataBase.storage);
    }

    @Test
    public void testPurchase_ok() {
        expected = new HashMap<>();
        expected.put(fruitApple, 0);
        CustomDataBase.storage.put(fruitApple, value);
        PURCHASE_OPERATION.changeValue(FRUIT_DAO, fruitApple, value);
        Assert.assertEquals(expected, CustomDataBase.storage);
    }

    @Test
    public void testReturn_ok() {
        expected = new HashMap<>();
        expected.put(fruitApple, value + value);
        CustomDataBase.storage.put(fruitApple, value);
        RETURN_OPERATION.changeValue(FRUIT_DAO, fruitApple, value);
        Assert.assertEquals(expected, CustomDataBase.storage);
    }

    @Test
    public void testPrepareData_ok() {
        PreparingData preparingData = new PrepareDataImpl();
        expected = new HashMap<>();
        expected.put(fruitApple, value);
        String expectedPrep = "fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator();
        String actual = preparingData.prepare(expected);
        Assert.assertEquals(expectedPrep,actual);
    }

    @Test
    public void testDao() {
        Fruit [] fruits = {
                new Fruit("apple"),
                new Fruit("banana")
        };
        CustomDataBase.storage.put(fruits[0],value);
        CustomDataBase.storage.put(fruits[1],value);
        int expectedLength = fruits.length;
        int actualLength = CustomDataBase.storage.size();
        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testGetQuantityOfFruit_ok() {
        Integer actual = value;
        CustomDataBase.storage.put(new Fruit("apple"), value);
        Assert.assertEquals(FRUIT_DAO.getQuantity(fruitApple), actual);
    }

    @Test
    public void testGetAllMethod() {
        final Map<Fruit, Integer> actual = new HashMap<>();
        Map<Fruit, Integer> expected;
        CustomDataBase.storage.put(fruitApple, value);
        CustomDataBase.storage.put(fruitBanana, value);
        expected = FRUIT_DAO.getAll();
        actual.put(fruitApple, value);
        actual.put(fruitBanana, value);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void after() {
        CustomDataBase.storage.clear();
    }
}

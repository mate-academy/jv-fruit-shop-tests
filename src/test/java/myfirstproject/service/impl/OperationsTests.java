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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

public class OperationsTests {
    private static final FruitDao FRUIT_DAO = new FruitDaoImpl();
    private static final SeparationOfOperations SEPARATION = new SeparationOfOperations();
    private static final List<String[]> TEMPORAL_LIST = new ArrayList<>();
    private static final OperationHandler BALANCE_OPERATION = new BalanceOperation();
    private static final OperationHandler PURCHASE_OPERATION = new PurchaseOperation();
    private static final OperationHandler RETURN_OPERATION = new ReturnOperation();
    private static final OperationHandler SUPPLY_OPERATION = new SupplyOperation();
    private static Map<String, OperationHandler> operation;
    private final Fruit fruit = new Fruit("apple");
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
    }

    @Test
    public void testBalanceIsNull_ok() {
        expected = new HashMap<>();
        expected.put(fruit, value);
        CustomDataBase.storage.put(fruit, value);
        BALANCE_OPERATION.changeValue(FRUIT_DAO, fruit, value);
        Integer actual = CustomDataBase.storage.get(fruit);
        Assert.assertEquals(expected.get(fruit), actual);
    }

    @Test
    public void testSupplyIsNull_ok() {
        expected = new HashMap<>();
        expected.put(fruit, value + value);
        CustomDataBase.storage.put(fruit, value);
        SUPPLY_OPERATION.changeValue(FRUIT_DAO, fruit, value);
        Integer actual = CustomDataBase.storage.get(fruit);
        Assert.assertEquals(expected.get(fruit), actual);
    }

    @Test
    public void testPurchaseIsNull_ok() {
        expected = new HashMap<>();
        expected.put(fruit, 0);
        CustomDataBase.storage.put(fruit, value);
        PURCHASE_OPERATION.changeValue(FRUIT_DAO, fruit, value);
        Integer actual = CustomDataBase.storage.get(fruit);
        Assert.assertEquals(expected.get(fruit), actual);
    }

    @Test
    public void testReturnIsNull_ok() {
        expected = new HashMap<>();
        expected.put(fruit, value + value);
        CustomDataBase.storage.put(fruit, value);
        RETURN_OPERATION.changeValue(FRUIT_DAO, fruit, value);
        Integer actual = CustomDataBase.storage.get(fruit);
        Assert.assertEquals(expected.get(fruit), actual);
    }

    @Test
    public void testPrepareData_ok() {
        PreparingData preparingData = new PrepareDataImpl();
        expected = new HashMap<>();
        expected.put(fruit, value);
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
        Integer actual = 10;
        Assert.assertEquals(FRUIT_DAO.getQuantity(fruit), actual);
    }

    @Test
    public void testGetAllMethod() {
        Map<Fruit, Integer> actual = new HashMap<>();
        Map<Fruit, Integer> expected = FRUIT_DAO.getAll();
        actual.put(new Fruit("apple"), 10);
        actual.put(new Fruit("banana"), 10);
        Assert.assertEquals(expected, actual);
    }

    @AfterAll
    public static void after() {
        operation.clear();
    }
}

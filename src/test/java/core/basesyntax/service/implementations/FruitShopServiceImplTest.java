package core.basesyntax.service.implementations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exeptions.InvalidQuantityException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.dto.TransactionDto;
import core.basesyntax.service.operations.OperationDecreaseHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.OperationIncreaseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static FruitDao fruitDao;
    private static List<TransactionDto> transactionList;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        Map<String, OperationHandler> handlers = new HashMap<>();
        handlers.put("b", new OperationIncreaseHandler(fruitDao));
        handlers.put("s", new OperationIncreaseHandler(fruitDao));
        handlers.put("p", new OperationDecreaseHandler(fruitDao));
        handlers.put("r", new OperationIncreaseHandler(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        fruitShopService = new FruitShopServiceImpl(operationStrategy, fruitDao);
        transactionList = new ArrayList<>();
    }

    @After
    public void tearDown() {
        fruitDao.getAll().clear();
        transactionList.clear();
    }

    @Test
    public void fruitShopServiceImplTest_saveData_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 25);
        transactionList.add(new TransactionDto(OperationType.BALANCE,
                new Fruit("banana"), 25));
        saveToStorage();
        Assert.assertEquals(expected, fruitDao.getAll());
    }

    @Test
    public void fruitShopServiceImplTest_saveData_supplyOperation_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 25);
        transactionList.add(new TransactionDto(OperationType.SUPPLY,
                new Fruit("banana"), 25));
        saveToStorage();
        Assert.assertEquals(expected, fruitDao.getAll());
    }

    @Test
    public void fruitShopServiceImplTest_saveData_returnOperation_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 25);
        transactionList.add(new TransactionDto(OperationType.RETURN,
                new Fruit("banana"), 25));
        saveToStorage();
        Assert.assertEquals(expected, fruitDao.getAll());
    }

    @Test
    public void fruitShopServiceImplTest_saveData_purchaseOperation_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 25);
        transactionList.add(new TransactionDto(OperationType.RETURN,
                new Fruit("banana"), 30));
        transactionList.add(new TransactionDto(OperationType.PURCHASE,
                new Fruit("banana"), 5));
        saveToStorage();
        Assert.assertEquals(expected, fruitDao.getAll());
    }

    @Test(expected = InvalidQuantityException.class)
    public void fruitShopServiceImplTest_saveData_purchaseOperation_NotOk() {
        transactionList.add(new TransactionDto(OperationType.PURCHASE,
                new Fruit("banana"), 25));
        saveToStorage();
    }

    @Test
    public void fruitShopServiceImplTest_createReport_Ok() {
        transactionList.add(new TransactionDto(OperationType.BALANCE,
                new Fruit("banana"), 20));
        transactionList.add(new TransactionDto(OperationType.BALANCE,
                new Fruit("apple"), 100));
        transactionList.add(new TransactionDto(OperationType.SUPPLY,
                new Fruit("banana"), 100));
        transactionList.add(new TransactionDto(OperationType.PURCHASE,
                new Fruit("banana"), 13));
        transactionList.add(new TransactionDto(OperationType.RETURN,
                new Fruit("apple"), 10));
        transactionList.add(new TransactionDto(OperationType.PURCHASE,
                new Fruit("apple"), 20));
        transactionList.add(new TransactionDto(OperationType.PURCHASE,
                new Fruit("banana"), 5));
        transactionList.add(new TransactionDto(OperationType.SUPPLY,
                new Fruit("banana"), 50));
        saveToStorage();

        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        String actual = fruitShopService.createReport();
        Assert.assertEquals(expected, actual);
    }

    private static void saveToStorage() {
        fruitShopService.saveData(transactionList);
    }
}

package core.basesyntax.service.shop;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.exception.EmptyStorageException;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Product;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.operation.OperationDecreaseHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationIncreaseHandler;
import core.basesyntax.service.shop.impl.FruitShopServiceImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceTest {
    private static FruitShopService shopService;
    private static ProductDao productDao;
    private static OperationStrategy operationStrategy;
    private static List<TransactionDto> transactionDtoList;

    @BeforeClass
    public static void beforeClass() {
        productDao = new ProductDaoImpl();
        operationStrategy = new OperationStrategyImpl(getOperationHandlerMap(productDao));
        shopService = new FruitShopServiceImpl(operationStrategy, productDao);
        transactionDtoList = new ArrayList<>();
    }

    @Test
    public void saveDataToStorageWithBalanceOperation_isOk() {
        Map<Product, Integer> exceptedStorage = new HashMap<>();
        exceptedStorage.put(new Product("banana"), 100);
        transactionDtoList.add(new TransactionDto(
                Operation.BALANCE,new Product("banana"), 100));
        saveToStorage();
        Assert.assertEquals(exceptedStorage, productDao.getAll());
    }

    @Test
    public void saveDataToStorageWithSupplyOperation_isOk() {
        Map<Product, Integer> exceptedStorage = new HashMap<>();
        exceptedStorage.put(new Product("apple"), 25);
        transactionDtoList.add(new TransactionDto(
                Operation.SUPPLY,new Product("apple"), 25));
        saveToStorage();
        Assert.assertEquals(exceptedStorage, productDao.getAll());
    }

    @Test
    public void saveDataToStorageWithReturnOperation_isOk() {
        Map<Product, Integer> exceptedStorage = new HashMap<>();
        exceptedStorage.put(new Product("orange"), 30);
        transactionDtoList.add(new TransactionDto(
                Operation.RETURN,new Product("orange"), 30));
        saveToStorage();
        Assert.assertEquals(exceptedStorage, productDao.getAll());
    }

    @Test
    public void saveDataToStorageWithPurchaseOperation_isOk() {
        Map<Product, Integer> exceptedStorage = new HashMap<>();
        exceptedStorage.put(new Product("orange"), 20);
        transactionDtoList.add(new TransactionDto(
                Operation.BALANCE,new Product("orange"), 30));
        transactionDtoList.add(new TransactionDto(
                Operation.PURCHASE, new Product("orange"),10));
        saveToStorage();
        Assert.assertEquals(exceptedStorage, productDao.getAll());
    }

    @Test(expected = EmptyStorageException.class)
    public void saveDataToStorageWithPurchaseOperationWhenStorageIsEmpty_notOk() {
        transactionDtoList.add(new TransactionDto(
                Operation.PURCHASE, new Product("apple"),10));
        saveToStorage();
    }

    @Test
    public void saveDataToStorage_isOk() {
        Map<Product, Integer> exceptedStorage = new HashMap<>();
        exceptedStorage.put(new Product("banana"), 155);
        exceptedStorage.put(new Product("apple"), 45);
        setTransactionDtoList();
        saveToStorage();
        Assert.assertEquals(exceptedStorage, productDao.getAll());
    }

    @Test
    public void createReport_isOk() {
        setTransactionDtoList();
        saveToStorage();
        String exceptedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,155" + System.lineSeparator()
                + "apple,45" + System.lineSeparator();
        String actualReport = shopService.createReport();
        Assert.assertEquals(exceptedReport, actualReport);
    }

    @Test
    public void createReportWhenStorageIsEmpty_isOk() {
        String exceptedReport = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals(exceptedReport, shopService.createReport());
    }

    @After
    public void tearDown() {
        productDao.getAll().clear();
        transactionDtoList.clear();
    }

    private static Map<String, OperationHandler> getOperationHandlerMap(ProductDao productDao) {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new OperationIncreaseHandler(productDao));
        operationHandlerMap.put("s", new OperationIncreaseHandler(productDao));
        operationHandlerMap.put("r", new OperationIncreaseHandler(productDao));
        operationHandlerMap.put("p", new OperationDecreaseHandler(productDao));
        return operationHandlerMap;
    }

    private static void setTransactionDtoList() {
        transactionDtoList.add(new TransactionDto(Operation.BALANCE, new Product("banana"), 80));
        transactionDtoList.add(new TransactionDto(Operation.BALANCE, new Product("apple"), 30));
        transactionDtoList.add(new TransactionDto(Operation.PURCHASE, new Product("banana"), 20));
        transactionDtoList.add(new TransactionDto(Operation.PURCHASE, new Product("apple"), 10));
        transactionDtoList.add(new TransactionDto(Operation.RETURN, new Product("banana"), 80));
        transactionDtoList.add(new TransactionDto(Operation.RETURN, new Product("apple"), 30));
        transactionDtoList.add(new TransactionDto(Operation.SUPPLY, new Product("banana"), 15));
        transactionDtoList.add(new TransactionDto(Operation.PURCHASE, new Product("apple"), 5));
    }

    private static void saveToStorage() {
        shopService.saveData(transactionDtoList);
    }
}

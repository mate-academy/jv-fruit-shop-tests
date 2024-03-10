package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.Operation;
import core.basesyntax.operations.Operations;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StoreServiceImplTest {
    private static final String INPUT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";
    private static StoreService storeService;
    private static ProductDao productDao;
    private static List<String> correctData;
    private static List<String> wrongValue;
    private static List<String> wrongOperation;
    private static Map<Product, Integer> expectedMap;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<Operations, Operation> operationMap = new HashMap<>();
        operationMap.put(Operations.B, new BalanceOperation());
        operationMap.put(Operations.S, new SupplyOperation());
        operationMap.put(Operations.R, new ReturnOperation());
        operationMap.put(Operations.P, new PurchaseOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationMap);
        productDao = new ProductDaoImpl();
        storeService = new StoreServiceImpl(operationStrategy, productDao);

        correctData = new ArrayList<>();
        correctData.add("type,fruit,quantity");
        correctData.add("b,banana,20");
        correctData.add("b,apple,100");
        correctData.add("s,banana,100");
        correctData.add("p,banana,13");
        correctData.add("r,apple,10");
        correctData.add("p,apple,20");
        correctData.add("p,banana,5");
        correctData.add("s,banana,50");

        wrongOperation = new ArrayList<>();
        wrongOperation.add("a,apple,20");
        wrongOperation.add("z,apple,20");
        wrongOperation.add("b,apple,20");

        wrongValue = new ArrayList<>();
        wrongValue.add("b,banana,20");
        wrongValue.add("b,banana,-20");
        wrongValue.add("b,banana,-290");

        expectedMap = new HashMap<>();
        expectedMap.put(new Product("banana"), 152);
        expectedMap.put(new Product("apple"), 90);
    }

    @Test
    public void addCorrectInput_Ok() {
        storeService.addToStorage(correctData);
        Map<Product, Integer> actual = Storage.products;
        assertEquals(expectedMap, actual);
    }

    @Test(expected = RuntimeException.class)
    public void addIncorrectOperation_NotOk() {
        storeService.addToStorage(wrongOperation);
    }

    @Test(expected = RuntimeException.class)
    public void addIncorrectValue_NotOk() {
        storeService.addToStorage(wrongValue);
    }

    @Test
    public void takeReportFromStorage() {
        Storage.products.put(new Product("banana"), 152);
        Storage.products.put(new Product("apple"), 90);
        String actual = storeService.getTheReportFromTheStorage();
        assertEquals(INPUT, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.products.clear();
    }
}

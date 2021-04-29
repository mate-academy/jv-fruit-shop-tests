package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.Operation;
import core.basesyntax.operations.Operations;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.strategy.OperationsStrategy;
import core.basesyntax.strategy.OperationsStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class WarehouseServiceImplTest {
    private static final String INPUT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static WarehouseService warehouseService;
    private static ProductDao productDao;
    private static List<String> correctDataFromFile;
    private static List<String> dataWithWrongOperation;
    private static List<String> dataWithWrongAmount;
    private static Map<Product, Integer> expectedMap;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<Operations, Operation> operationMap = new HashMap<>();
        operationMap.put(Operations.B, new BalanceOperation());
        operationMap.put(Operations.S, new SupplyOperation());
        operationMap.put(Operations.P, new PurchaseOperation());
        operationMap.put(Operations.R, new ReturnOperation());

        OperationsStrategy operationsStrategy = new OperationsStrategyImpl(operationMap);
        productDao = new ProductDaoImpl();
        warehouseService = new WarehouseServiceImpl(operationsStrategy, productDao);

        correctDataFromFile = new ArrayList<>();
        correctDataFromFile.add("type,fruit,quantity");
        correctDataFromFile.add("b,banana,20");
        correctDataFromFile.add("b,apple,100");
        correctDataFromFile.add("s,banana,100");
        correctDataFromFile.add("p,banana,13");
        correctDataFromFile.add("r,apple,10");
        correctDataFromFile.add("p,apple,20");
        correctDataFromFile.add("p,banana,5");
        correctDataFromFile.add("s,banana,50");

        dataWithWrongOperation = new ArrayList<>();
        dataWithWrongOperation.add("type,fruit,quantity");
        dataWithWrongOperation.add("b,apple,100");
        dataWithWrongOperation.add("z,banana,20");
        dataWithWrongOperation.add("b,banana,20");

        dataWithWrongAmount = new ArrayList<>();
        dataWithWrongAmount.add("b,banana,20");
        dataWithWrongAmount.add("b,apple,100");
        dataWithWrongAmount.add("s,banana,-100");
        dataWithWrongAmount.add("p,banana,-13");

        expectedMap = new HashMap<>();
        expectedMap.put(new Product("banana"), 152);
        expectedMap.put(new Product("apple"), 90);
    }

    @Test
    public void addToStorageTestWithCorrectData_Ok() {
        warehouseService.addToStorage(correctDataFromFile);
        Map<Product, Integer> actual = Storage.getProducts();
        assertEquals(expectedMap, actual);
    }

    @Test(expected = RuntimeException.class)
    public void addToStorageTestWithWrongOperationType_NotOk() {
        warehouseService.addToStorage(dataWithWrongOperation);
    }

    @Test(expected = RuntimeException.class)
    public void addToStorageTestWithWrongAmount_NotOk() {
        warehouseService.addToStorage(dataWithWrongAmount);
    }

    @Test
    public void getReportFromStorage_Ok() {
        Storage.getProducts().put(new Product("banana"), 152);
        Storage.getProducts().put(new Product("apple"), 90);
        String actual = warehouseService.getReportFromStorage();
        assertEquals(INPUT, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.getProducts().clear();
    }
}

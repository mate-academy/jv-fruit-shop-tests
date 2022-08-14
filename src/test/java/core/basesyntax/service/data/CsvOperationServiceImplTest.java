package core.basesyntax.service.data;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvOperationServiceImplTest {
    private static CsvOperationServiceImpl csvOperationService;
    private static FruitDaoImpl fruitDao;
    private static List<FruitTransaction> fruitTransactions;
    private static Map<String, Integer> storage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        storage = FruitStorage.storage;
        storage.clear();
        HashMap<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = new HashMap<>() {{
                        put(FruitTransaction.Operation.BALANCE,
                                new BalanceOperationHandler(fruitDao));
                        put(FruitTransaction.Operation.SUPPLY,
                                new SupplyOperationHandler(fruitDao));
                        put(FruitTransaction.Operation.PURCHASE,
                                new PurchaseOperationHandler(fruitDao));
                        put(FruitTransaction.Operation.RETURN,
                                new ReturnOperationHandler(fruitDao));
                        }};
        csvOperationService = new CsvOperationServiceImpl(operationHandlerMap);
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
    }

    @Test
    public void processOperation_validOperation_ok() {
        fruitTransactions = List.of(new FruitTransaction("b", "banana", 10));
        csvOperationService.processOperation(fruitTransactions);
        String expectedValue = "{banana=10}";
        String actualValues = storage.toString();
        Assert.assertEquals(expectedValue, actualValues);
        fruitTransactions = List.of(new FruitTransaction("s", "banana", 50));
        csvOperationService.processOperation(fruitTransactions);
        expectedValue = "{banana=60}";
        actualValues = storage.toString();
        Assert.assertEquals(expectedValue, actualValues);
        fruitTransactions = List.of(new FruitTransaction("p", "banana", 20));
        csvOperationService.processOperation(fruitTransactions);
        expectedValue = "{banana=40}";
        actualValues = storage.toString();
        Assert.assertEquals(expectedValue, actualValues);
        fruitTransactions = List.of(new FruitTransaction("r", "banana", 30));
        csvOperationService.processOperation(fruitTransactions);
        expectedValue = "{banana=70}";
        actualValues = storage.toString();
        Assert.assertEquals(expectedValue, actualValues);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_invalidOperation_ok() {
        fruitTransactions = List.of(new FruitTransaction("w", "banana", 10));
        csvOperationService.processOperation(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_nullList_notOk() {
        csvOperationService.processOperation(null);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_negativeBalance_notOk() {
        fruitTransactions = List.of(new FruitTransaction("b", "banana", 10),
                new FruitTransaction("p", "banana", 20));
        csvOperationService.processOperation(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_multipleBalance_notOk() {
        fruitTransactions = List.of(new FruitTransaction("b", "banana", 10),
                new FruitTransaction("b", "banana", 20));
        csvOperationService.processOperation(fruitTransactions);
    }
}

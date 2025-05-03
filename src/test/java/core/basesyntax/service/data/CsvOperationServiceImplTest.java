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

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
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
    public void setUp() {
        FruitStorage.storage.clear();
    }

    @Test
    public void processOperation_validOperation_Ok() {
        List<FruitTransaction> fruitTransactions;
        fruitTransactions = List.of(new FruitTransaction("b", "banana", 10));
        csvOperationService.processOperation(fruitTransactions);
        Map<String, Integer> expectedValue = new HashMap<>() {{
                put("banana", 10);
                }};
        Map<String, Integer> actualValues = FruitStorage.storage;
        Assert.assertEquals(expectedValue, actualValues);
        fruitTransactions = List.of(new FruitTransaction("s", "banana", 50));
        csvOperationService.processOperation(fruitTransactions);
        expectedValue = new HashMap<>() {{
                put("banana", 60);
                }};
        Assert.assertEquals(expectedValue, actualValues);
        fruitTransactions = List.of(new FruitTransaction("p", "banana", 20));
        csvOperationService.processOperation(fruitTransactions);
        expectedValue = new HashMap<>() {{
                put("banana", 40);
                }};
        Assert.assertEquals(expectedValue, actualValues);
        fruitTransactions = List.of(new FruitTransaction("r", "banana", 30));
        csvOperationService.processOperation(fruitTransactions);
        expectedValue = new HashMap<>() {{
                put("banana", 70);
                }};
        Assert.assertEquals(expectedValue, actualValues);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_invalidOperation_Ok() {
        List<FruitTransaction> fruitTransactions;
        fruitTransactions = List.of(new FruitTransaction("w", "banana", 10));
        csvOperationService.processOperation(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_nullList_notOk() {
        csvOperationService.processOperation(null);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_negativeBalance_notOk() {
        List<FruitTransaction> fruitTransactions;
        fruitTransactions = List.of(new FruitTransaction("b", "banana", 10),
                new FruitTransaction("p", "banana", 20));
        csvOperationService.processOperation(fruitTransactions);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_multipleBalance_notOk() {
        List<FruitTransaction> fruitTransactions;
        fruitTransactions = List.of(new FruitTransaction("b", "banana", 10),
                new FruitTransaction("b", "banana", 20));
        csvOperationService.processOperation(fruitTransactions);
    }
}

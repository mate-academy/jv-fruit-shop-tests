package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.ParseFileImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationHandlerBalance;
import core.basesyntax.strategy.impl.OperationHandlerPurchase;
import core.basesyntax.strategy.impl.OperationHandlerReturn;
import core.basesyntax.strategy.impl.OperationHandlerSupply;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String APPLE = "apple";
    private static final Integer TEST_START_VALUE = 60;
    private static final Integer TEST_PURCHASE_VALUE = 30;
    private static final Integer TEST_BALANCE_VALUE = 30;
    private static final Integer TEST_RETURN_VALUE = 90;
    private static final Integer TEST_SUPPLY_VALUE = 90;
    private static final String HEAD = "type,fruit,quantity";
    private static final String SECOND_LINE_PURCHASE = "p,apple,30";
    private static final String SECOND_LINE_BALANCE = "b,apple,30";
    private static final String SECOND_LINE_RETURN = "r,apple,30";
    private static final String SECOND_LINE_SUPPLY = "s,apple,30";
    private static List<FruitTransaction> fruitTransactions;
    private static List<String> data;
    private static OperationStrategy chooseOperation;
    private static FruitServiceImpl fruitService;
    private static Map<FruitTransaction.Operation, OperationHandler> strategies;
    private static StorageDao storageDao;
    private static Map<String, Integer> expectedStorage;

    @Before
    public void setUp() {
        data = new ArrayList<>();
        storageDao = new StorageDaoImpl();
        expectedStorage = new HashMap<>();
        strategies = new HashMap<>();
        chooseOperation = new OperationStrategyImpl(strategies);
        fruitService = new FruitServiceImpl(chooseOperation);
    }

    @Test
    public void servicePurchase_ok() {
        data.add(HEAD);
        data.add(SECOND_LINE_PURCHASE);
        fruitTransactions = new ParseFileImpl().parseData(data);
        Storage.fruitStorage.put(APPLE, TEST_START_VALUE);
        expectedStorage.put(APPLE, TEST_PURCHASE_VALUE);
        initialize();
        fruitService.apply(fruitTransactions);
        Assert.assertEquals(expectedStorage, storageDao.getFruitStorage());
    }

    @Test
    public void serviceBalance_ok() {
        data.add(HEAD);
        data.add(SECOND_LINE_BALANCE);
        fruitTransactions = new ParseFileImpl().parseData(data);
        Storage.fruitStorage.put(APPLE,TEST_BALANCE_VALUE);
        expectedStorage.put(APPLE, TEST_PURCHASE_VALUE);
        initialize();
        fruitService.apply(fruitTransactions);
        Assert.assertEquals(expectedStorage, storageDao.getFruitStorage());
    }

    @Test
    public void serviceReturn_ok() {
        data.add(HEAD);
        data.add(SECOND_LINE_RETURN);
        fruitTransactions = new ParseFileImpl().parseData(data);
        Storage.fruitStorage.put(APPLE,TEST_START_VALUE);
        expectedStorage.put(APPLE, TEST_RETURN_VALUE);
        initialize();
        fruitService.apply(fruitTransactions);
        Assert.assertEquals(expectedStorage, storageDao.getFruitStorage());
    }

    @Test
    public void serviceSupply_ok() {
        data.add(HEAD);
        data.add(SECOND_LINE_SUPPLY);
        fruitTransactions = new ParseFileImpl().parseData(data);
        Storage.fruitStorage.put(APPLE,TEST_START_VALUE);
        expectedStorage.put(APPLE, TEST_SUPPLY_VALUE);
        initialize();
        fruitService.apply(fruitTransactions);
        Assert.assertEquals(expectedStorage, storageDao.getFruitStorage());
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
        data.clear();
        expectedStorage.clear();
        strategies.clear();
        fruitTransactions.clear();
    }

    private void initialize() {
        strategies.put(FruitTransaction.Operation.BALANCE, new OperationHandlerBalance());
        strategies.put(FruitTransaction.Operation.SUPPLY, new OperationHandlerSupply());
        strategies.put(FruitTransaction.Operation.PURCHASE, new OperationHandlerPurchase());
        strategies.put(FruitTransaction.Operation.RETURN, new OperationHandlerReturn());
    }
}

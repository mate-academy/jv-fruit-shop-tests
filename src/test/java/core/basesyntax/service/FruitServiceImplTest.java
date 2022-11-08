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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.basesyntax.strategy.impl.OperationStrategyImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String APPLE = "apple";
    private static final Integer TEST_START_VALUE = 60;
    private static final Integer TEST_PURCHASE_VALUE = 30;
    private static final String HEAD = "type,fruit,quantity";
    private static final String SECOND_LINE = "p,apple,30";
    private static List<FruitTransaction> fruitTransactions;
    private static List<String> data;
    private static OperationStrategy chooseOperation;
    private static FruitServiceImpl fruitService;
    private static Map<FruitTransaction.Operation, OperationHandler> strategies;
    private static StorageDao storageDao;
    private static Map<String, Integer> expectedStorage;

    public static void initializationMethod() {
        strategies = new HashMap<>();
        strategies.put(FruitTransaction.Operation.BALANCE, new OperationHandlerBalance());
        strategies.put(FruitTransaction.Operation.SUPPLY, new OperationHandlerSupply());
        strategies.put(FruitTransaction.Operation.PURCHASE, new OperationHandlerPurchase());
        strategies.put(FruitTransaction.Operation.RETURN, new OperationHandlerReturn());
    }

    @BeforeClass
    public static void beforeClass() {
        data = new ArrayList<>();
        storageDao = new StorageDaoImpl();
        expectedStorage = new HashMap<>();
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void serviceWithExistOperation_ok() {
        Storage.fruitStorage.put(APPLE, TEST_START_VALUE);
        data.add(HEAD);
        data.add(SECOND_LINE);
        expectedStorage.put(APPLE, TEST_PURCHASE_VALUE);
        fruitTransactions = new ParseFileImpl().parseData(data);
        initializationMethod();
        chooseOperation = new OperationStrategyImpl(strategies);
        fruitService = new FruitServiceImpl(chooseOperation);
        fruitService.apply(fruitTransactions);
        Assert.assertEquals(expectedStorage, storageDao.getFruitStorage());
    }
}

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

    @BeforeClass
    public static void beforeClass() {
        data = new ArrayList<>();
        storageDao = new StorageDaoImpl();
        expectedStorage = new HashMap<>();
        strategies = new HashMap<>();
        data.add(HEAD);
        data.add(SECOND_LINE);
        chooseOperation = new OperationStrategyImpl(strategies);
        fruitService = new FruitServiceImpl(chooseOperation);
        fruitTransactions = new ParseFileImpl().parseData(data);
        strategies.put(FruitTransaction.Operation.BALANCE, new OperationHandlerBalance());
        strategies.put(FruitTransaction.Operation.SUPPLY, new OperationHandlerSupply());
        strategies.put(FruitTransaction.Operation.PURCHASE, new OperationHandlerPurchase());
        strategies.put(FruitTransaction.Operation.RETURN, new OperationHandlerReturn());
    }

    @Test
    public void serviceWithExistOperation_ok() {
        Storage.fruitStorage.put(APPLE, TEST_START_VALUE);
        expectedStorage.put(APPLE, TEST_PURCHASE_VALUE);
        fruitService.apply(fruitTransactions);
        Assert.assertEquals(expectedStorage, storageDao.getFruitStorage());
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitStorage.clear();
    }
}

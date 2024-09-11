package core.basesyntax.service.shopservice;

import core.basesyntax.TransactionSupplier;
import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ShopServiceImplTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_LEMON = "lemon";
    private static final String FRUIT_ORANGE = "orange";
    private static final Map<String, Integer> TEST_INPUT_DATA = Map.of(
            FRUIT_APPLE, 30,
            FRUIT_LEMON, 15,
            FRUIT_ORANGE, 25
    );
    private List<FruitTransaction> testTransactions;
    private OperationStrategy operationStrategy;
    private Map<Operation, OperationHandler> operationHandlerMap;
    private FruitStorageDao storageDao;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        storageDao = new FruitStorageDaoImpl();
        storageDao.getAllFruits().putAll(TEST_INPUT_DATA);
        testTransactions = List.of(
                TransactionSupplier.of(Operation.BALANCE, FRUIT_APPLE, 50),
                TransactionSupplier.of(Operation.RETURN, FRUIT_APPLE, 30),
                TransactionSupplier.of(Operation.SUPPLY, FRUIT_APPLE, 40),
                TransactionSupplier.of(Operation.RETURN, FRUIT_LEMON, 20),
                TransactionSupplier.of(Operation.BALANCE, FRUIT_LEMON, 10));
        operationHandlerMap = Map.of(
                Operation.BALANCE, new BalanceOperation(storageDao),
                Operation.PURCHASE, new PurchaseOperation(storageDao),
                Operation.RETURN, new ReturnOperation(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy, storageDao);
    }

    @Test
    void totalQuantityAfterOperationBalance_ok() {
        int totalQuantity = storageDao.calculateTotalQuantity();
        shopService.process(testTransactions);
    }
}
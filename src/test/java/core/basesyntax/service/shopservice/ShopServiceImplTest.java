package core.basesyntax.service.shopservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.TransactionSupplier;
import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.BalanceOperation;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.strategy.PurchaseOperation;
import core.basesyntax.service.strategy.ReturnOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_LEMON = "lemon";
    private static final String FRUIT_ORANGE = "orange";
    private static final int EXPECTED_QUANTITY = 75;
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
                TransactionSupplier.of(Operation.PURCHASE, FRUIT_APPLE, 40),
                TransactionSupplier.of(Operation.RETURN, FRUIT_LEMON, 20),
                TransactionSupplier.of(Operation.BALANCE, FRUIT_LEMON, 10));
        operationHandlerMap = Map.of(
                Operation.BALANCE, new BalanceOperation(storageDao),
                Operation.PURCHASE, new PurchaseOperation(storageDao),
                Operation.RETURN, new ReturnOperation(storageDao)
        );
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy, storageDao);
    }

    @Test
    void totalQuantityAfterOperationBalance_ok() {
        int totalQuantityAfter = shopService.process(testTransactions);
        assertEquals(totalQuantityAfter, EXPECTED_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        storageDao.getAllFruits().clear();
    }
}

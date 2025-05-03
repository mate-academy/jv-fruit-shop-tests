package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.impl.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitStorageService;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageServiceImplTest {
    private static final String FRUIT_APPLE = "apple";
    private FruitStorageService fruitStorageService;
    private OperationStrategy operationStrategy;
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap;
        operationHandlerMap = Map.of(Operation.BALANCE, new BalanceOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitStorageDao = new FruitStorageDaoImpl();
        fruitStorageService = new FruitStorageServiceImpl(fruitStorageDao, operationStrategy);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void processTransactions_validTransactions_Ok() {
        FruitTransaction transaction1 = new FruitTransaction(Operation.BALANCE,
                FRUIT_APPLE, 80);
        FruitTransaction transaction2 = new FruitTransaction(Operation.PURCHASE,
                FRUIT_APPLE, 50);
        List<FruitTransaction> input = new ArrayList<>();
        input.add(transaction1);
        input.add(transaction2);
        fruitStorageService.processTransactions(input);
        assertEquals(30, FruitStorage.fruitStorage.get(FRUIT_APPLE),
                "Fruit quantity is expected to be 30, but was:"
                        + FruitStorage.fruitStorage.get("apple"));
    }

    @Test
    void processTransactions_negativeStorage_NotOk() {
        FruitTransaction transaction1 = new FruitTransaction(Operation.BALANCE,
                FRUIT_APPLE, 80);
        FruitTransaction transaction2 = new FruitTransaction(Operation.PURCHASE,
                FRUIT_APPLE, 100);
        List<FruitTransaction> input = List.of(transaction1, transaction2);
        assertThrows(IllegalArgumentException.class,
                () -> fruitStorageService.processTransactions(input),
                "IllegalArgumentException is expected");
    }
}

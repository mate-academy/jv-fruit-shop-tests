package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.service.StoreService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StoreServiceImplTest {
    private Map<Operation, OperationHandler> operationHandlersMap = new HashMap<>();
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);
    private StoreService storeService = new StoreServiceImpl(operationStrategy);

    @AfterEach
    void tearDown() {
        FruitStorage.FRUITS.clear();
    }

    @Test
    void putToMap_validInputObjectsOfFruitTransaction_ok() {
        operationHandlersMap.put(Operation.BALANCE, new BalanceOperationHandler());
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        fruitTransactionList.add(new FruitTransaction(Operation.BALANCE, "apple", 100));
        storeService.putToMap(fruitTransactionList);
        int actual = FruitStorage.FRUITS.entrySet().size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void putToMap_nullInputParameter_notOk() {
        assertThrows(RuntimeException.class, () -> {
            storeService.putToMap(null);
        });
    }
}

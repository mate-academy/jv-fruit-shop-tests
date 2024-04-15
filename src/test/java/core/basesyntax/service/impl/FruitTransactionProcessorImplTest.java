package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operations.strategy.BalanceOperationHandler;
import core.basesyntax.service.operations.strategy.OperationHandler;
import core.basesyntax.service.operations.strategy.PurchaseOperationHandler;
import core.basesyntax.service.operations.strategy.ReturnOperationHandler;
import core.basesyntax.service.operations.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {
    private static final FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoImpl();

    private static FruitTransactionProcessorImpl fruitTransactionProcessor;

    @BeforeAll
    public static void init() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));

        fruitTransactionProcessor =
                new FruitTransactionProcessorImpl(new OperationStrategyImpl(operationHandlerMap));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void process_normalData_ok() {
        List<FruitTransaction> list = new ArrayList<>();

        list.add(new FruitTransaction(Operation.BALANCE, "banana", 100));
        list.add(new FruitTransaction(Operation.SUPPLY, "banana", 30));
        list.add(new FruitTransaction(Operation.PURCHASE, "banana", 20));
        list.add(new FruitTransaction(Operation.RETURN, "banana", 1));

        fruitTransactionProcessor.process(list);

        assertEquals(Storage.fruits.get("banana"), 111);
    }

    @Test
    public void process_empty_ok() {
        List<FruitTransaction> list = new ArrayList<>();

        fruitTransactionProcessor.process(list);

        assertTrue(Storage.fruits.isEmpty());
    }

    @Test
    public void process_null_ok() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionProcessor.process(null);
        });
    }
}

package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static FruitDao fruitDao;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationStrategy> operationHandlers = new HashMap<>();

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    void setUp() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitDao));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitDao));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitDao));
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void balanceOperation_addsNewFruitWithQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50);
        OperationStrategy handler = operationHandlers.get(fruitTransaction.getOperation());
        handler.execute(fruitTransaction);
        int actual = storage.get("banana");
        int expected = 50;
        assertEquals(actual, expected);
    }

    @Test
    void supplyOperation_addsNewFruitWithQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50);
        OperationStrategy handler = operationHandlers.get(fruitTransaction.getOperation());
        handler.execute(fruitTransaction);
        int actual = storage.get("banana");
        int expected = 50;
        assertEquals(actual, expected);
    }

    @Test
    void returnOperation_returnFruitWithQuantity_ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 50);
        OperationStrategy handler = operationHandlers.get(fruitTransaction.getOperation());
        handler.execute(fruitTransaction);
        int actual = storage.get("banana");
        int expected = 50;
        assertEquals(actual, expected);
    }

    @Test
    void purchaseOperation_reducesFruitWithQuantity_ok() {
        storage.put("banana", 52);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50);
        OperationStrategy handler = operationHandlers.get(fruitTransaction.getOperation());
        handler.execute(fruitTransaction);
        int actual = storage.get("banana");
        int expected = 2;
        assertEquals(actual, expected);
    }
}

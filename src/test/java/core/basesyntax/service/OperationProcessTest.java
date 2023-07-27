package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.operations.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationProcessTest {
    private static OperationProcess operationProcess;
    private static OperationHandler operationHandler;
    private static OperationStrategy operationStrategy;
    private static List<FruitTransaction> fruitTransactionList;
    private static final int STANDARD_STORAGE_QUANTITY = 400;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl();
        fruitTransactionList = new ArrayList<>();
        Storage.storage.clear();
    }

    @BeforeEach
    void beforeEach() {
        Storage.storage.put("banana", STANDARD_STORAGE_QUANTITY);
    }

    @Test
    void operationProcess_BalanceOperation_Ok() {
        fruitTransactionList.add(new FruitTransaction(Operation.BALANCE, new Fruit("banana", 200)));
        operationHandler = operationStrategy.get(fruitTransactionList.get(0).getOperation());
        operationProcess = new OperationProcessImpl(operationStrategy);
        operationProcess.processTransactions(fruitTransactionList);
        assertEquals(Storage.storage.get("banana"), 200);
    }

    @Test
    void operationProcess_PurchaseOperation_Ok() {
        fruitTransactionList.add(new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 50)));
        operationHandler = operationStrategy.get(fruitTransactionList.get(0).getOperation());
        operationProcess = new OperationProcessImpl(operationStrategy);
        operationProcess.processTransactions(fruitTransactionList);
        assertEquals(Storage.storage.get("banana"), 350);
    }

    @Test
    void operationProcess_SupplyOperation_Ok() {
        fruitTransactionList.add(new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 100)));
        operationHandler = operationStrategy.get(fruitTransactionList.get(0).getOperation());
        operationProcess = new OperationProcessImpl(operationStrategy);
        operationProcess.processTransactions(fruitTransactionList);
        assertEquals(Storage.storage.get("banana"), 500);
    }

    @Test
    void operationProcess_ReturnOperation_Ok() {
        fruitTransactionList.add(new FruitTransaction(Operation.RETURN, new Fruit("banana", 75)));
        operationHandler = operationStrategy.get(fruitTransactionList.get(0).getOperation());
        operationProcess = new OperationProcessImpl(operationStrategy);
        operationProcess.processTransactions(fruitTransactionList);
        assertEquals(Storage.storage.get("banana"), 475);
    }

    @Test
    void operationProcess_PurchaseBiggerThanExist() {
        fruitTransactionList.add(new FruitTransaction(Operation.PURCHASE,
                new Fruit("banana", 600)));
        operationHandler = operationStrategy.get(fruitTransactionList.get(0).getOperation());
        operationProcess = new OperationProcessImpl(operationStrategy);
        assertThrows(RuntimeException.class,
                () -> operationProcess.processTransactions(fruitTransactionList));
    }

    @AfterEach
    void afterEach() {
        fruitTransactionList.clear();
        Storage.storage.clear();
    }
}

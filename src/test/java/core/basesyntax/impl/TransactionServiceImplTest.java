package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.impl.operation.OperationStrategy;
import core.basesyntax.impl.operation.OperationStrategyImpl;
import core.basesyntax.impl.operation.operations.BalanceHandlerImpl;
import core.basesyntax.impl.operation.operations.OperationHandler;
import core.basesyntax.impl.operation.operations.PurchaseHandlerImpl;
import core.basesyntax.impl.operation.operations.ReturnHandlerImpl;
import core.basesyntax.impl.operation.operations.SupplyHandlerImpl;
import core.basesyntax.impl.operation.operations.exception.NegativeBalanceException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private static List<FruitTransaction> fruitTransactions;
    private static Map<String, Integer> expectedStorage;
    private static Map<Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static TransactionService transactionService;

    @BeforeAll
    static void beforeAll() {
        expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 152);
        expectedStorage.put("apple", 90);
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionService = new TransactionServiceImpl(operationStrategy);
        Storage.STORAGE.clear();
    }

    @Test
    void transactionService_validInfo_ok() {
        fruitTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 50)
        );
        transactionService.processTransactions(fruitTransactions);
        Assertions.assertEquals(expectedStorage, Storage.STORAGE);
    }

    @Test
    void transactionService_negativeBalance_notOk() {
        fruitTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 5000),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 50)
        );
        Assertions.assertThrows(NegativeBalanceException.class,
                () -> transactionService.processTransactions(fruitTransactions));
    }
}

package core.basesyntax.service.impl;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageServiceImplTest {

    private FruitStorageService fruitStorageService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation
                .PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        FruitStorageDao fruitStorageDao = new FruitStorageDaoImpl();
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitStorageService = new FruitStorageServiceImpl(fruitStorageDao, operationStrategy);
    }

    @AfterEach
    void clear() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void processTransaction_balanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 100);
        fruitStorageService.processTransactions(Collections.singletonList(transaction));
        Assertions.assertEquals(100, FruitStorage.fruitStorage.get("apple"));
    }

    @Test
    void processTransaction_supplyOperation_ok() {
        FruitStorage.fruitStorage.put("banana", 50);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "banana", 30);

        fruitStorageService.processTransactions(Collections.singletonList(transaction));

        Assertions.assertEquals(80, FruitStorage.fruitStorage.get("banana"));
    }

    @Test
    void processTransaction_purchaseOperation_ok() {
        FruitStorage.fruitStorage.put("orange", 50);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "orange", 20);
        fruitStorageService.processTransactions(Collections.singletonList(transaction));
        Assertions.assertEquals(30, FruitStorage.fruitStorage.get("orange"));
    }

    @Test
    void processTransaction_returnOperation_ok() {
        FruitStorage.fruitStorage.put("mango", 40);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.RETURN, "mango", 15);
        fruitStorageService.processTransactions(Collections.singletonList(transaction));
        Assertions.assertEquals(55, FruitStorage.fruitStorage.get("mango"));
    }
}

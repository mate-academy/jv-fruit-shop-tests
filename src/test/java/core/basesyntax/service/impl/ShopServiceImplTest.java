package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;
    private List<FruitTransaction> transactionList;
    private FruitTransaction.Operation operation;
    private String fruit;
    private int quantity;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction
                .Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        transactionList = new ArrayList<>();
        operation = FruitTransaction.Operation.BALANCE;
        fruit = "banana";
        quantity = 3;
    }

    @Test
    void process_fruitTransactionListNullValue_NotOk() {
        assertThrows(RuntimeException.class, () -> shopService.process(null));
    }

    @Test
    void process_fruitNumberInTransactionsEqualToEntitiesInOutput_Ok() {
        transactionList.add(new FruitTransaction(operation, fruit, quantity));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "raspberry", 35));
        transactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "strawberry", 56));
        shopService.process(transactionList);
        assertEquals(3, Storage.storage.entrySet().size());
    }
}

package core.basesyntax.service.impl;

import core.basesyntax.FruitTransaction;
import core.basesyntax.Storage;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.OperationStrategy;
import core.basesyntax.operations.OperationStrategyImpl;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private ShopServiceImpl shopService;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy, storage);
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",
                    100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana",
                    30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10)
        );
        shopService.process(transactions);
        Assertions.assertEquals(130, storage.getAllFruits().get("banana"),
                "Wrong final banana quantity.");
    }

    @Test
    void process_purchaseMoreThanAvailable_exceptionThrown() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",
                    100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana",
                    150)
        );
        Assertions.assertThrows(RuntimeException.class, () -> {
            shopService.process(transactions);
        }, "Expected RuntimeException when purchasing more than available.");
    }
}

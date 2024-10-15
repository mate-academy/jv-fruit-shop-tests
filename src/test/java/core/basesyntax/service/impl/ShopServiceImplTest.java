package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final List<FruitTransaction> CORRECT_FRUIT_TRANSACTION_LIST = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
    );
    private static final Map<String, Integer> CORRECT_STORAGE = Map.of(
            "banana", 152,
            "apple", 90
    );
    private static ShopService shopService;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(storage),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(storage),
                FruitTransaction.Operation.RETURN, new ReturnOperation(storage),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(storage)
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        storage.clearStorage();
    }

    @Test
    void process_correctData_ok() {
        shopService.process(CORRECT_FRUIT_TRANSACTION_LIST);
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = CORRECT_STORAGE;
        Assertions.assertEquals(expected, actual);
    }
}

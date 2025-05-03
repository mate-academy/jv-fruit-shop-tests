package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final List<FruitTransaction> CORRECT_FRUIT_TRANSACTION_LIST = List.of(
            new FruitTransaction(BALANCE, "banana", 20),
            new FruitTransaction(BALANCE, "apple", 100),
            new FruitTransaction(SUPPLY, "banana", 100),
            new FruitTransaction(PURCHASE, "banana", 13),
            new FruitTransaction(RETURN, "apple", 10),
            new FruitTransaction(PURCHASE, "apple", 20),
            new FruitTransaction(PURCHASE, "banana", 5),
            new FruitTransaction(SUPPLY, "banana", 50)
    );
    private static final List<FruitTransaction> EMPTY_LIST = new ArrayList<>();
    private static final Map<String, Integer> CORRECT_STORAGE = Map.of(
            "banana", 152,
            "apple", 90
    );
    private static ShopService shopService;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        Map<Operation, OperationHandler> operationHandlers = Map.of(
                BALANCE, new BalanceOperation(storage),
                PURCHASE, new PurchaseOperation(storage),
                RETURN, new ReturnOperation(storage),
                SUPPLY, new SupplyOperation(storage)
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

    @Test
    void process_emptyList_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> shopService.process(EMPTY_LIST),
                "Expected RuntimeException was not thrown in " + ShopServiceImpl.class);
    }
}

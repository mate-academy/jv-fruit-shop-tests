package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static final Map<String, Integer> RESULT_MAP = Map.of(
            "apple", 160,
            "banana", 60);
    private static final List<FruitTransaction> INPUT_TRANSACTION_LIST = List.of(
            new FruitTransaction(Operation.BALANCE, "apple", 100),
            new FruitTransaction(Operation.BALANCE, "banana", 50),
            new FruitTransaction(Operation.SUPPLY, "apple", 100),
            new FruitTransaction(Operation.SUPPLY, "banana", 50),
            new FruitTransaction(Operation.PURCHASE, "apple", 30),
            new FruitTransaction(Operation.PURCHASE, "banana", 20),
            new FruitTransaction(Operation.PURCHASE, "apple", 20),
            new FruitTransaction(Operation.PURCHASE, "banana", 30),
            new FruitTransaction(Operation.RETURN, "apple", 10),
            new FruitTransaction(Operation.RETURN, "banana", 10));
    private static final Map<Operation, OperationHandler> operationHandlerMapFull = Map.of(
            Operation.BALANCE, new BalanceHandler(),
            Operation.SUPPLY, new SupplyHandler(),
            Operation.RETURN, new ReturnHandler(),
            Operation.PURCHASE, new PurchaseHandler());
    private static final Map<Operation, OperationHandler> operationHandlerMapNotFull = Map.of(
            Operation.BALANCE, new BalanceHandler(),
            Operation.SUPPLY, new SupplyHandler(),
            Operation.RETURN, new ReturnHandler());
    private static final Map<Operation, OperationHandler> operationHandlerEmptyMap = Map.of();

    @BeforeEach
    void setUP() {
        Storage.fruitStorage.clear();
    }

    @Test
    void processTransactions_filledMap_Ok() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMapFull);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);

        fruitShopService.processTransactions(INPUT_TRANSACTION_LIST);
        Assertions.assertEquals(RESULT_MAP, Storage.fruitStorage);
    }

    @Test
    void processTransactions_filledMap_NotOk() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMapNotFull);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);

        Assertions.assertThrows(RuntimeException.class,
                () -> fruitShopService.processTransactions(INPUT_TRANSACTION_LIST));
    }

    @Test
    void processTransactions_emptyMap_NotOk() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerEmptyMap);
        FruitShopService fruitShopService = new FruitShopServiceImpl(operationStrategy);

        Assertions.assertThrows(RuntimeException.class,
                () -> fruitShopService.processTransactions(INPUT_TRANSACTION_LIST));
    }

    @Test
    void init_nullPointer_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> new FruitShopServiceImpl(null));
    }
}

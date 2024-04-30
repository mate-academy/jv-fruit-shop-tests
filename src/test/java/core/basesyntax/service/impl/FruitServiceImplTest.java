package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.activities.BalanceHandler;
import core.basesyntax.strategy.activities.OperationHandler;
import core.basesyntax.strategy.activities.PurchaseHandler;
import core.basesyntax.strategy.activities.ReturnHandler;
import core.basesyntax.strategy.activities.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static final String BANANA_FRUIT = "banana";
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            Map.of(
                    FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnHandler()
            );
    private static final Map<String,Integer> VALID_TEST = Map.of(
            "banana", 50
    );
    private FruitService fruitService;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        fruitService = new FruitServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        storage = new StorageImpl();
    }

    @Test
    public void processTransactions_PositiveResult_Ok() {
        List<FruitTransaction> transactions1 = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA_FRUIT, 40),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA_FRUIT, 10)

        );
        fruitService.processTransactions(transactions1);

        int expected = VALID_TEST.get(BANANA_FRUIT);
        int actual = storage.getValue(BANANA_FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void processTransactions_nullFruit_NotOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        null,
                        10);

        assertThrows(RuntimeException.class, () ->
                new BalanceHandler().executeTransaction(transaction));
    }

    @AfterEach
    void afterEach() {
        new StorageImpl().clear();
    }
}

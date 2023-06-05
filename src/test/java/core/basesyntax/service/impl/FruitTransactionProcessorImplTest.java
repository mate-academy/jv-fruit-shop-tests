package core.basesyntax.service.impl;

import core.basesyntax.db.MapStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.strategy.OperationHandlerStrategy;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {
    private FruitTransactionProcessorImpl fruitTransactionProcessor;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new MapStorage(); // Use your preferred storage implementation here
        FruitService fruitService = new FruitServiceImpl(storage);
        OperationHandlerStrategy strategy = new OperationHandlerStrategyImpl(
                Map.of(
                        FruitTransaction.Operation.BALANCE,
                            new BalanceOperationHandler(fruitService),
                        FruitTransaction.Operation.SUPPLY,
                            new SupplyOperationHandler(fruitService),
                        FruitTransaction.Operation.RETURN,
                            new ReturnOperationHandler(fruitService),
                        FruitTransaction.Operation.PURCHASE,
                            new PurchaseOperationHandler(fruitService)
                )
        );
        fruitTransactionProcessor = new FruitTransactionProcessorImpl(
                strategy
        );
    }

    @Test
    void process_emptyFruitTransactionList_ok() {
        fruitTransactionProcessor.process(List.of());
        Assertions.assertTrue(storage.getAll().isEmpty());
    }

    @Test
    void process_getByCode_ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode("s");
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        Assert.assertEquals(actual, expected);
    }

    @Test
    void process_getByCode_notOk() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> FruitTransaction.Operation.getByCode("j")
        );
    }

    @Test
    void process_validFruitTransaction_notOk() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> {
                    List<FruitTransaction> fruitTransactions = List.of(
                            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                    "watermelon",
                                    10
                            )
                    );
                    fruitTransactionProcessor.process(fruitTransactions);
                }
        );
    }

    @Test
    void process_nonEmptyFruitTransactionList_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE,
                        "apple",
                        30
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE,
                        "orange",
                        100
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "apple",
                        25
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN,
                        "apple",
                        5
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY,
                        "orange",
                        14
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE,
                        "orange",
                        60
                )
        );
        fruitTransactionProcessor.process(fruitTransactions);
        Map<String, Integer> expected = Map.of(
                "apple", 10,
                "orange", 54
        );
        Map<String, Integer> actual = storage.getAll();
        Assertions.assertEquals(expected, actual);
    }
}

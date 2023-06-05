package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.MapStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.FruitTransactionProcessorImpl;
import core.basesyntax.service.strategy.OperationHandlerStrategy;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String TEST_FRUIT_NAME = "watermelon";
    private static FruitTransactionProcessorImpl fruitTransactionProcessor;
    private static Storage storage;

    @BeforeEach
    void setUp() {
        storage = new MapStorage();
        FruitService fruitService = new FruitServiceImpl(storage);
        OperationHandlerStrategy strategy = new OperationHandlerStrategyImpl(
                Map.of(
                        FruitTransaction.Operation.BALANCE,
                        new BalanceOperationHandler(fruitService),
                        FruitTransaction.Operation.SUPPLY,
                        new SupplyOperationHandler(fruitService)
                )
        );
        fruitTransactionProcessor = new FruitTransactionProcessorImpl(
                strategy
        );
    }

    @Test
    void handle_supplyOperationValidInput_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE,
                        "apple",
                        5
                ),
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY,
                        "apple",
                        10
                )
        );
        fruitTransactionProcessor.process(fruitTransactions);
        Map<String, Integer> expected = Map.of(
                "apple", 15
        );
        Map<String, Integer> actual = storage.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void handle_supplyOperationInvalidFruitName_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> {
                    List<FruitTransaction> fruitTransactions = List.of(
                            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                                    TEST_FRUIT_NAME,
                                    10
                            )
                    );
                    fruitTransactionProcessor.process(fruitTransactions);
                },
                "There is no fruit like this in storage! " + "(" + TEST_FRUIT_NAME + ")"
        );
    }

    @Test
    void handle_supplyOperationInvalidQuantity_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> {
                    List<FruitTransaction> fruitTransactions = List.of(
                            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                    TEST_FRUIT_NAME,
                                    25
                            ),
                            new FruitTransaction(FruitTransaction.Operation.RETURN,
                                    TEST_FRUIT_NAME,
                                    -5
                            )
                    );
                    fruitTransactionProcessor.process(fruitTransactions);
                },
                "Enter valid quantity"
        );
    }
}

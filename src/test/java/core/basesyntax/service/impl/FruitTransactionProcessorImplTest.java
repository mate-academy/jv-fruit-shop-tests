package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {
    private static FruitTransactionProcessorImpl fruitTransactionProcessor;
    private static Storage storage;
    private static final String INVALID_OPERATION_CODE = "j";
    private static final String VALID_OPERATION_CODE = "s";

    @BeforeAll
    static void setUp() {
        storage = new MapStorage();
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
        assertTrue(storage.getAll().isEmpty());
    }

    @Test
    void process_getByCode_ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation
                .getByCode(VALID_OPERATION_CODE);
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        assertEquals(actual, expected);
    }

    @Test
    void process_getByCode_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> FruitTransaction.Operation.getByCode(INVALID_OPERATION_CODE),
                "There is not operation with code: " + INVALID_OPERATION_CODE
        );
    }
}

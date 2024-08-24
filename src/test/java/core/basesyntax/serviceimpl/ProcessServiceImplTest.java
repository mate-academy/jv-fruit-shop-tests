package core.basesyntax.serviceimpl;

import static core.basesyntax.db.Storage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.ReturnOperation;
import core.basesyntax.operation.SupplyOperation;
import core.basesyntax.strategy.StrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProcessServiceImplTest {
    private static final List<FruitTransaction> TRANSACTIONS_TO_PROCESS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana",
                    20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "apple",
                    100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "banana",
                    100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "banana",
                    13),
            new FruitTransaction(FruitTransaction.Operation.RETURN,
                    "apple",
                    10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "apple",
                    20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "banana",
                    5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "banana",
                    50)
    );
    private static final Map<FruitTransaction.Operation, OperationHandler> OPERATION_HANDLER_MAP =
            Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperation(),
            FruitTransaction.Operation.RETURN, new ReturnOperation(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperation()
    );
    private static final String BANANA_TRANSACTION_NAME = "banana";
    private static final String APPLE_TRANSACTION_NAME = "apple";
    private static final int EXPECTED_BANANA_RESULT = 152;
    private static final int EXPECTED_APPLE_RESULT = 90;
    private static ProcessServiceImpl processService;

    @BeforeAll
    static void beforeAll() {
        StrategyImpl processStrategy = new StrategyImpl(OPERATION_HANDLER_MAP);
        processService = new ProcessServiceImpl(processStrategy);
    }

    @Test
    void process_validInputAddedToStorage_Ok() {
        processService.process(TRANSACTIONS_TO_PROCESS);
        assertEquals(EXPECTED_BANANA_RESULT, storage.get(BANANA_TRANSACTION_NAME));
        assertEquals(EXPECTED_APPLE_RESULT, storage.get(APPLE_TRANSACTION_NAME));
    }
}

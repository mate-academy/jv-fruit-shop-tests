package core.basesyntax.service.implemantation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionExecutorImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 50;
    private static final String EXPECTED_ERROR_MASSAGE_STRATEGY_NULL =
            "Strategy map can`t be a null";
    private static final Map<Operation, OperationHandler> operationHandlerMap = Map.of(
            Operation.BALANCE, new BalanceHandler());
    private static TransactionExecutor transactionExecutor;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeAll
    static void beforeAll() {
        transactionExecutor = new TransactionExecutorImpl();
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(Operation.BALANCE, FRUIT_NAME, FRUIT_QUANTITY));
    }

    @Test
    void processDate_correctExecutor_ok() {
        transactionExecutor.processDate(fruitTransactions, operationHandlerMap);

        assertEquals(FRUIT_QUANTITY, Storage.storage.get(FRUIT_NAME));

        Storage.storage.clear();
    }

    @Test
    void processDate_strategyNull_notOk() {
        var actual = assertThrows(RuntimeException.class,
                () -> transactionExecutor.processDate(fruitTransactions, null));

        assertEquals(EXPECTED_ERROR_MASSAGE_STRATEGY_NULL, actual.getMessage());
    }
}

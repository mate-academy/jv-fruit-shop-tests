package core.basesyntax.service.implemantation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseHandler;
import core.basesyntax.handlers.ReturnHandler;
import core.basesyntax.handlers.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionExecutorImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 50;
    private static final int COUNT_FRUIT = 10;
    private static final int EXPECTED_FRUIT_QUANTITY = 60;
    private static final Map<Operation, OperationHandler> operationHandlerMap = Map.of(
            Operation.BALANCE, new BalanceHandler(),
            Operation.SUPPLY, new SupplyHandler(),
            Operation.PURCHASE, new PurchaseHandler(),
            Operation.RETURN, new ReturnHandler());

    private TransactionExecutor transactionExecutor;

    @BeforeEach
    void setUp() {
        transactionExecutor = new TransactionExecutorImpl();
    }

    @Test
    void transaction_CorrectExecutor_Ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(Operation.BALANCE, FRUIT_NAME, FRUIT_QUANTITY));
        fruitTransactions.add(new FruitTransaction(Operation.SUPPLY, FRUIT_NAME, COUNT_FRUIT));
        fruitTransactions.add(new FruitTransaction(Operation.RETURN, FRUIT_NAME, COUNT_FRUIT));
        fruitTransactions.add(new FruitTransaction(Operation.PURCHASE, FRUIT_NAME, COUNT_FRUIT));
        transactionExecutor.processDate(fruitTransactions, operationHandlerMap);
        assertEquals(EXPECTED_FRUIT_QUANTITY, Storage.storage.get(FRUIT_NAME));
        Storage.storage.clear();
    }
}

package core.basesyntax.serviseimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseHandler;
import core.basesyntax.handlers.ReturnHandler;
import core.basesyntax.handlers.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataProcessorImplTest {
    private DataProcessorImpl dataProcessor;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> operationHandlers = Map.of(
                Operation.BALANCE, new BalanceHandler(),
                Operation.SUPPLY, new SupplyHandler(),
                Operation.PURCHASE, new PurchaseHandler(),
                Operation.RETURN, new ReturnHandler()
        );
        dataProcessor = new DataProcessorImpl(operationHandlers);
    }

    @Test
    void processTransactions_ValidInput_ReturnsCorrectResult_Ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(Operation.BALANCE, "apple", 2),
                new FruitTransaction(Operation.BALANCE, "banana", 1)
        );
        Map<String, Integer> expected = Map.of(
                "apple", 2,
                "banana", 1
        );

        Map<String, Integer> actual = dataProcessor.processTransactions(transactions);

        assertEquals(expected, actual);
    }
}

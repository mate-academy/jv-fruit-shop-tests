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
<<<<<<< HEAD
=======
import java.util.HashMap;
>>>>>>> b1ba0f15adc0cb3d923444ff62d9ee1bcae63315
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
<<<<<<< HEAD
    void processTransactions_ValidInput_ReturnsCorrectResult_Ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(Operation.BALANCE, "apple", 2),
                new FruitTransaction(Operation.BALANCE, "banana", 1)
        );
        Map<String, Integer> expected = Map.of(
                "apple", 2,
                "banana", 1
        );
=======
    void processTransactions_ValidInput_ShouldReturnCorrectResult() {
        List<FruitTransaction> transactions = Arrays.asList(
            new FruitTransaction(Operation.BALANCE, "apple", 2),
            new FruitTransaction(Operation.BALANCE, "banana", 1)
        );
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 2);
        expected.put("banana", 1);
>>>>>>> b1ba0f15adc0cb3d923444ff62d9ee1bcae63315

        Map<String, Integer> actual = dataProcessor.processTransactions(transactions);

        assertEquals(expected, actual);
    }
}

package core.basesyntax;

import core.basesyntax.strategy.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

class MainTest {
    private static OperationStrategy operationStrategy;
    private static TransactionHandler transactionHandler;

    @BeforeAll
    static void setUp() {
        Map<String, Transaction> transactionMap = Map.of(
                "b", new BalanceTransaction(),
                "s", new SupplyTransaction(),
                "p", new PurchaseTransaction(),
                "r", new ReturnTransaction()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(transactionMap);
        TransactionHandler transactionHandler = new TransactionHandler(operationStrategy);
    }

    @Test
    void transaction_improperOperation_notOk() {
        String line = "x,banana,10";
        assertThrows(IllegalArgumentException.class, () -> transactionHandler.process(line));
    }

    @Test
    void transaction_improperInput_notOk() {
        String line = "Hello, World";
        assertThrows(IllegalArgumentException.class, () -> transactionHandler.process(line));
    }

    @Test
    void transaction_nullInput_notOk() {
        String line = null;
        assertThrows(IllegalArgumentException.class, () -> transactionHandler.process(line));
    }
}

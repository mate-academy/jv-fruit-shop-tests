package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionHandlerTest {
    private OperationStrategy operationStrategy;
    private TransactionHandler transactionHandler;

    @BeforeEach
    void setUp() {
        Map<String, Transaction> transactionMap = Map.of(
                "b", new BalanceTransaction()
        );
        operationStrategy = new OperationStrategyImpl(transactionMap);
        transactionHandler = new TransactionHandler(operationStrategy);
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    private int getQuantity(String name) {
        return Storage.getFruits().stream()
                .filter(f -> f.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "Expected fruit not found in storage: " + name))
                .getQuantity();
    }

    @Test
    void process_inputBalanceTransaction_ok() {
        int expectedBalance = 20;
        String expectedFruit = "banana";
        String line = "b," + expectedFruit + "," + expectedBalance;
        transactionHandler.process(line);
        assertEquals(expectedBalance, getQuantity(expectedFruit),
                "Expected quantity was "
                        + expectedBalance
                        + " but received "
                        + getQuantity(expectedFruit));
        assertEquals(1, Storage.getFruits().size(), "Storage should contain exactly one fruit");
    }

    @Test
    void process_inputMissingElement_notOk() {
        String line = "b,banana";
        assertThrows(IllegalArgumentException.class,
                () -> transactionHandler.process(line));
    }

    @Test
    void process_tooManyElements_notOk() {
        String line = "b,banana,10,extra";
        assertThrows(IllegalArgumentException.class,
                () -> transactionHandler.process(line));
    }

    @Test
    void process_incorrectAmount_notOk() {
        String line = "b,banana,-2";
        assertThrows(IllegalArgumentException.class,
                () -> transactionHandler.process(line));
    }

    @Test
    void process_incorrectAmountType_notOk() {
        String line = "b,banana,abc";
        assertThrows(IllegalArgumentException.class,
                () -> transactionHandler.process(line));
    }
}

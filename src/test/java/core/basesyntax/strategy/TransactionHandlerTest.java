package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
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
                "b", new BalanceTransaction(),
                "s", new SupplyTransaction(),
                "p", new PurchaseTransaction(),
                "r", new ReturnTransaction()
        );
        operationStrategy = new OperationStrategyImpl(transactionMap);
        transactionHandler = new TransactionHandler(operationStrategy);
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    private void seedFruit(String name, int quantity) {
        Fruit fruit = new Fruit();
        fruit.setName(name);
        fruit.setQuantity(quantity);
        Storage.addFruit(fruit);
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
    void process_inputPurchaseTransaction_ok() {
        String expectedFruit = "banana";
        int startBalance = 50;
        int purchase = 30;
        int expectedBalance = startBalance - purchase;
        seedFruit(expectedFruit,startBalance);
        String line = "p," + expectedFruit + "," + purchase;
        transactionHandler.process(line);
        assertEquals(expectedBalance, getQuantity(expectedFruit),
                "Expected quantity was "
                        + expectedBalance
                        + " but received "
                        + getQuantity(expectedFruit));
        assertEquals(1, Storage.getFruits().size(), "Storage should contain exactly one fruit");
    }

    @Test
    void process_inputReturnTransaction_ok() {
        String expectedFruit = "banana";
        int startBalance = 50;
        int returned = 30;
        int expectedBalance = startBalance + returned;
        seedFruit(expectedFruit,startBalance);
        String line = "r," + expectedFruit + "," + returned;
        transactionHandler.process(line);
        assertEquals(expectedBalance, getQuantity(expectedFruit),
                "Expected quantity was "
                        + expectedBalance
                        + " but received "
                        + getQuantity(expectedFruit));
        assertEquals(1, Storage.getFruits().size(), "Storage should contain exactly one fruit");
    }

    @Test
    void process_inputSupplyTransaction_ok() {
        String expectedFruit = "banana";
        int startBalance = 50;
        int supplied = 30;
        int expectedBalance = startBalance + supplied;
        seedFruit(expectedFruit,startBalance);
        String line = "s," + expectedFruit + "," + supplied;
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
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> transactionHandler.process(line));
    }

    @Test
    void process_tooManyElements_notOk() {
        String line = "b,banana,10,extra";
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> transactionHandler.process(line));
    }

    @Test
    void process_incorrectAmount_notOk() {
        String line = "b,banana,-2";
        assertThrows(IllegalArgumentException.class,
                () -> transactionHandler.process(line));
    }
}

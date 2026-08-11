package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
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
        List<String> lines = List.of("b," + expectedFruit + "," + expectedBalance);
        for (String line : lines) {
            transactionHandler.process(line);
        }
        assertEquals(expectedBalance, getQuantity(expectedFruit));
    }

    @Test
    void process_inputPurchaseTransaction_ok() {
        String expectedFruit = "banana";
        int startBalance = 50;
        int purchase = 30;
        int expectedBalance = startBalance - purchase;
        seedFruit(expectedFruit,startBalance);
        List<String> lines = List.of("p," + expectedFruit + "," + purchase);
        for (String line : lines) {
            transactionHandler.process(line);
        }
        assertEquals(expectedBalance, getQuantity(expectedFruit));
    }

    @Test
    void process_inputReturnTransaction_ok() {
        String expectedFruit = "banana";
        int startBalance = 50;
        int returned = 30;
        int expectedBalance = startBalance + returned;
        seedFruit(expectedFruit,startBalance);
        List<String> lines = List.of("r," + expectedFruit + "," + returned);
        for (String line : lines) {
            transactionHandler.process(line);
        }
        assertEquals(expectedBalance, getQuantity(expectedFruit));
    }

    @Test
    void process_inputSupplyTransaction_ok() {
        String expectedFruit = "banana";
        int startBalance = 50;
        int supplied = 30;
        int expectedBalance = startBalance + supplied;
        seedFruit(expectedFruit,startBalance);
        List<String> lines = List.of("s," + expectedFruit + "," + supplied);
        for (String line : lines) {
            transactionHandler.process(line);
        }
        assertEquals(expectedBalance, getQuantity(expectedFruit));
    }

    @Test
    void process_inputMissingElement_notOk() {
        List<String> lines = List.of("b,banana");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            for (String line : lines) {
                transactionHandler.process(line);
            }
        });
    }

    @Test
    void process_tooManyElements_notOk() {
        List<String> lines = List.of("b,banana,10,extra");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            for (String line : lines) {
                transactionHandler.process(line);
            }
        });
    }

    @Test
    void process_incorrectAmount_notOk() {
        List<String> lines = List.of("b,banana,-2");
        assertThrows(IllegalArgumentException.class, () -> {
            for (String line : lines) {
                transactionHandler.process(line);
            }
        });
    }
}

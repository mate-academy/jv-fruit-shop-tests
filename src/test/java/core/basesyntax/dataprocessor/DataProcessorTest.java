package core.basesyntax.dataprocessor;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FruitDB;
import core.basesyntax.service.FruitTransaction;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataProcessorTest {
    private DataProcessor dataProcessor;

    @BeforeEach
    void setUp() {
        FruitDB.getInventory().clear();

        DefaultDataOperationStrategy operationStrategy = new DefaultDataOperationStrategy(
                Map.of(
                        Operation.BALANCE, new BalanceHandler(),
                        Operation.SUPPLY, new SupplyHandler(),
                        Operation.PURCHASE, new PurchaseHandler(),
                        Operation.RETURN, new ReturnHandler()
                )
        );
        dataProcessor = new DataProcessor(operationStrategy);
    }

    @Test
    void process_validTransactions_updatesInventoryCorrectly() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("b", "apple", 50),
                new FruitTransaction("s", "banana", 30),
                new FruitTransaction("p", "apple", 20)
        );
        dataProcessor.process(transactions);
        assertEquals(30, FruitDB.getInventory().get("apple").intValue());
        assertEquals(30, FruitDB.getInventory().get("banana").intValue());
    }

    @Test
    void process_emptyTransactionsList_doesNotChangeInventory() {
        List<FruitTransaction> transactions = List.of();
        dataProcessor.process(transactions);
        assertTrue(FruitDB.getInventory().isEmpty());
    }

    @Test
    void process_invalidOperation_throwsIllegalArgumentException() {
        List<FruitTransaction> transactions = List.of(new FruitTransaction("x", "apple", 50));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dataProcessor.process(transactions)
        );
        assertEquals("Invalid operation code: x", exception.getMessage());
    }

    @Test
    void process_negativeQuantity_throwsIllegalArgumentException() {
        List<FruitTransaction> transactions = List.of(new FruitTransaction("s", "apple", -10));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dataProcessor.process(transactions)
        );
        assertEquals("Transaction quantity cannot be negative", exception.getMessage());
    }
}

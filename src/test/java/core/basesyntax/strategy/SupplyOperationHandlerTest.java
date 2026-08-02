package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private final OperationHandler handler = new SupplyOperationHandler();

    @BeforeEach
    public void setUp() {
        Storage.clear();
    }

    @AfterEach
    public void tearDown() {
        Storage.clear();
    }

    @Test
    public void handle_existingFruit_increasesQuantity() {
        Storage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 5);

        handler.handle(transaction);

        assertEquals(25, Storage.getQuantity("banana"));
    }

    @Test
    public void handle_missingFruit_addsQuantity() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 5);

        handler.handle(transaction);

        assertEquals(5, Storage.getQuantity("banana"));
    }
}

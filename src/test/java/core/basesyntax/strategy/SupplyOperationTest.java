package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private OperationHandler supplyOperation;

    @BeforeEach
    public void setUp() {
        supplyOperation = new SupplyOperation();
    }

    @AfterEach
    public void tearDown() {
        Storage.getAllFruits().keySet().forEach(fruit -> Storage.updateQuantity(fruit, 0));
    }

    @Test
    public void handle_newFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "apple", 20);
        supplyOperation.handle(transaction);

        assertEquals(20, Storage.getQuantity("apple"));
    }
}





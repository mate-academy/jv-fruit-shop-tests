package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private OperationHandler returnOperation;

    @BeforeEach
    public void setUp() {
        returnOperation = new ReturnOperation();
    }

    @AfterEach
    public void tearDown() {
        Storage.getAllFruits().keySet().forEach(fruit -> Storage.updateQuantity(fruit, 0));
    }

    @Test
    public void handle_existingFruit_ok() {
        Storage.updateQuantity("orange", 10);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.RETURN, "orange", 5);
        returnOperation.handle(transaction);

        assertEquals(15, Storage.getQuantity("orange"));
    }
}





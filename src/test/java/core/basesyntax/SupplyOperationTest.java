package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        Storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_addNewFruit_ok() {
        String fruit = "apple";
        int quantity = 20;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, quantity);
        supplyOperation.handle(transaction);

        assertEquals(quantity, Storage.getAll().get(fruit));
    }

    @Test
    void handle_increaseExistingFruitQuantity_ok() {
        String fruit = "banana";
        int initialQuantity = 30;
        int quantityToAdd = 20;

        Storage.add(fruit, initialQuantity);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, quantityToAdd);
        supplyOperation.handle(transaction);
        int expectedBalance = initialQuantity + quantityToAdd;

        assertEquals(expectedBalance, Storage.getAll().get(fruit));
    }

    @Test
    void handle_negativeQuantity_throwsException() {
        String fruit = "apple";
        int negativeQuantity = -50;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, negativeQuantity
        );

        assertThrows(IllegalArgumentException.class, () -> supplyOperation.handle(transaction));

        RuntimeException exception = assertThrows(IllegalArgumentException.class, ()
                -> supplyOperation.handle(transaction));

        String expectedMessage = "Quantity cannot be negative";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }
}


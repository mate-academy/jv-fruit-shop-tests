package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        Storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_NewFruit_ok() {
        String fruit = "apple";
        int quantity = 120;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, fruit, quantity);
        returnOperation.handle(transaction);

        assertEquals(quantity, Storage.getAll().get(fruit));
    }

    @Test
    void handle_ExistingFruit_ok() {
        String fruit = "banana";
        int initialQuantity = 130;
        int quantityToAdd = 10;

        Storage.add(fruit, initialQuantity);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, fruit, quantityToAdd);
        returnOperation.handle(transaction);
        int expectedBalance = initialQuantity + quantityToAdd;

        assertEquals(expectedBalance, Storage.getAll().get(fruit));
    }

    @Test
    void handle_negativeQuantity_throwsException() {
        String fruit = "apple";
        int negativeQuantity = -50;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, fruit, negativeQuantity
        );

        assertThrows(IllegalArgumentException.class, () -> returnOperation.handle(transaction));

        RuntimeException exception = assertThrows(IllegalArgumentException.class, ()
                -> returnOperation.handle(transaction));

        String expectedMessage = "Quantity cannot be negative";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }
}

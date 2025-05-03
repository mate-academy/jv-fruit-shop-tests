package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.PurchaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        Storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_validTransaction_ok() {
        String fruit = "apple";
        int initialBalance = 100;
        int purchaseQuantity = 30;
        Storage.add(fruit, initialBalance);

        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, fruit, 30);
        purchaseOperation.handle(transaction);

        int expectedBalance = initialBalance - purchaseQuantity;
        assertEquals(expectedBalance, Storage.getAll().get(fruit));
    }

    @Test
    void handle_purchaseExactBalance_ok() {
        Storage.add("banana", 30);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 30);

        purchaseOperation.handle(transaction);
        assertEquals(0, Storage.getAll().get("banana"));
    }

    @Test
    void handle_purchaseMoreThanBalance_throwsException() {
        Storage.add("orange", 20);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 25);

        Exception exception = assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(transaction));

        assertEquals("Insufficient quantity of fruit: orange. Current balance: 20",
                exception.getMessage());
    }

    @Test
    void handle_purchaseNonexistentFruit_throwsException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 10);

        Exception exception = assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(transaction));

        assertEquals("Such fruit is not sold here: " + "orange",
                exception.getMessage());
    }

    @Test
    void handle_negativeQuantity_throwsException() {
        String fruit = "apple";
        int negativeQuantity = -50;
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, fruit, negativeQuantity
        );

        assertThrows(IllegalArgumentException.class, () -> purchaseOperation.handle(transaction));

        RuntimeException exception = assertThrows(IllegalArgumentException.class, ()
                -> purchaseOperation.handle(transaction));

        String expectedMessage = "Quantity cannot be negative";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage),
                "Expected exception message to contain: " + expectedMessage);
    }
}

package core.basesyntax.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.FruitStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {

    @BeforeEach
    void clearStock() {
        FruitStock.stock.clear();
    }

    @Test
    void handle_shouldDecreaseQuantity_whenEnoughStock() {
        FruitStock fruitStock = new FruitStock();
        PurchaseHandler purchaseHandler = new PurchaseHandler(fruitStock);
        fruitStock.add("apple", 30);
        purchaseHandler.handle("apple", 20);
        assertEquals(10, fruitStock.getQuantity("apple"));
    }

    @Test
    void handle_shouldThrowException_ifStockTooLow() {
        FruitStock fruitStock = new FruitStock();
        PurchaseHandler purchaseHandler = new PurchaseHandler(fruitStock);
        fruitStock.add("banana", 15);
        assertThrows(RuntimeException.class, () -> purchaseHandler.handle("banana", 20));
    }

    @Test
    void handle_shouldThrowException_ifFruitNotInStock() {
        FruitStock fruitStock = new FruitStock();
        PurchaseHandler purchaseHandler = new PurchaseHandler(fruitStock);
        assertThrows(RuntimeException.class, () -> purchaseHandler.handle("orange", 10));
    }

    @Test
    void handle_shouldNotChangeStock_ifExceptionThrown() {
        FruitStock fruitStock = new FruitStock();
        PurchaseHandler purchaseHandler = new PurchaseHandler(fruitStock);
        fruitStock.add("mango", 5);
        assertThrows(RuntimeException.class, () -> purchaseHandler.handle("mango", 10));
        assertEquals(5, fruitStock.getQuantity("mango"));
    }
}

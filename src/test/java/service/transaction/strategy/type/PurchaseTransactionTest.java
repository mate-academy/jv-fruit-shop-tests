package service.transaction.strategy.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import model.InvalidTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTransactionTest {
    private final TransactionHandler transactionHandler = new PurchaseTransaction();
    private Map<String, Integer> stock;

    @BeforeEach
    void beforeEach() {
        stock = new HashMap<>();
    }

    @Test
    void validCase_purchaseValidQuantity() {
        stock.put("banana", 140);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.PURCHASE, "banana", 120);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("banana", 20);
        assertEquals(expected, stock);
    }

    @Test
    void invalidCase_purchaseInvalidFruit() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.PURCHASE, "apple", 120);
        assertThrows(InvalidTransaction.class, () ->
                        transactionHandler.perform(stock, fruitTransaction),
                "Product does not exist in stock - apple");
    }

    @Test
    void invalidCase_purchaseInvalidQuantity() {
        stock.put("apple", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.PURCHASE, "apple", 120);
        assertThrows(InvalidTransaction.class, () ->
                        transactionHandler.perform(stock, fruitTransaction),
                "The transaction cannot be completed, "
                        + " quantity for sale - " + 120
                        + " is greater than the quantity in stock - " + 40);
    }

    @Test
    void validCase_purchaseNegativeQuantity() {
        stock.put("banana", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.PURCHASE, "banana", -10);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("banana", 50);
        assertEquals(expected, stock);
    }
}

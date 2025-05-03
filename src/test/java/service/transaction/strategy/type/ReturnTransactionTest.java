package service.transaction.strategy.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import model.InvalidTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnTransactionTest {
    private final TransactionHandler transactionHandler = new ReturnTransaction();
    private Map<String, Integer> stock;

    @BeforeEach
    void beforeEach() {
        stock = new HashMap<>();
    }

    @Test
    void validCase_returnExistedFruit() {
        stock.put("banana", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.RETURN, "banana", 120);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("banana", 160);
        assertEquals(expected, stock);
    }

    @Test
    void invalidCase_returnNewFruit() {
        stock.put("banana", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.RETURN, "apple", 120);
        assertThrows(InvalidTransaction.class, () ->
                transactionHandler.perform(stock, fruitTransaction),
                "Product which wasn't in the stock cannot be returned - apple");
    }

    @Test
    void validCase_returnNegativeQuantity() {
        stock.put("banana", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.RETURN, "banana", -10);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("banana", 30);
        assertEquals(expected, stock);
    }
}

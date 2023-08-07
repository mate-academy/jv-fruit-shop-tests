package service.transaction.strategy.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import model.InvalidTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTransactionTest {
    private final TransactionHandler transactionHandler = new BalanceTransaction();
    private Map<String, Integer> stock;

    @BeforeEach
    void beforeEach() {
        stock = new HashMap<>();
    }

    @Test
    void validCase_newFruit() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.BALANCE, "banana", 120);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("banana", 120);
        assertEquals(expected, stock);
    }

    @Test
    void validCase_existedFruitWithValidQuantity() {
        stock.put("apple", 120);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.BALANCE, "apple", 120);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("apple", 120);
        assertEquals(expected, stock);
    }

    @Test
    void invalidCase_existedFruitWithInvalidQuantity() {
        stock.put("apple", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.BALANCE, "apple", 120);
        assertThrows(InvalidTransaction.class, () ->
                        transactionHandler.perform(stock, fruitTransaction),
                "Product has already quantity in stock and it`s not " + 120);
    }
}

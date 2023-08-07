package service.transaction.strategy.type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyTransactionTest {
    private final TransactionHandler transactionHandler = new SupplyTransaction();
    private Map<String, Integer> stock;

    @BeforeEach
    void beforeEach() {
        stock = new HashMap<>();
    }

    @Test
    void validCase_supplyNewFruit() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY, "banana", 120);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("banana", 120);
        assertEquals(expected, stock);
    }

    @Test
    void validCase_supplyExistedFruit() {
        stock.put("banana", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY, "banana", 120);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("banana", 160);
        assertEquals(expected, stock);
    }

    @Test
    void validCase_supplyNegativeQuantity() {
        stock.put("banana", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.OperationType.SUPPLY, "banana", -10);
        transactionHandler.perform(stock, fruitTransaction);
        Map<String, Integer> expected = Map.of("banana", 30);
        assertEquals(expected, stock);
    }
}

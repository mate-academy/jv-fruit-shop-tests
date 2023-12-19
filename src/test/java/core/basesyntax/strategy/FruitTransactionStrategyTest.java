package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.FruitDB;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionStrategyTest {

    @BeforeEach
    public void setUp() {
        FruitDB.FRUIT_DATA_BASE.clear();
    }

    @Test
    public void testExecuteTransactions_BalanceOperation() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        fruitTransactionStrategy.executeTransactions(transactions);
        int expectedQuantity = 10;
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get("apple");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testExecuteTransactions_PurchaseOperation() {
        FruitDB.FRUIT_DATA_BASE.put("apple", 20);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 15));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        fruitTransactionStrategy.executeTransactions(transactions);
        int expectedQuantity = 5;
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get("apple");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testExecuteTransactions_SupplyOperation() {
        FruitDB.FRUIT_DATA_BASE.put("banana", 30);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 25));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        fruitTransactionStrategy.executeTransactions(transactions);
        int expectedQuantity = 55;
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get("banana");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testExecuteTransactions_ReturnOperation() {
        FruitDB.FRUIT_DATA_BASE.put("orange", 5);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 3));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        fruitTransactionStrategy.executeTransactions(transactions);
        int expectedQuantity = 8;
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get("orange");
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testExecuteTransactions_NotEnoughInventory() {
        FruitDB.FRUIT_DATA_BASE.put("peach", 3);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "peach", 5));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        assertThrows(RuntimeException.class, () ->
                fruitTransactionStrategy.executeTransactions(transactions));
    }
}

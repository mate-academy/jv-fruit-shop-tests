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
        String fruit = "apple";
        int quantity = 10;
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, quantity));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        fruitTransactionStrategy.executeTransactions(transactions);
        int expectedQuantity = 10;
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(fruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testExecuteTransactions_PurchaseOperation() {
        String fruit = "orange";
        int quantity = 20;
        FruitDB.FRUIT_DATA_BASE.put(fruit, quantity);
        List<FruitTransaction> transactions = new ArrayList<>();
        int purchaseQuantity = 15;
        transactions.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, fruit, purchaseQuantity));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        fruitTransactionStrategy.executeTransactions(transactions);
        int expectedQuantity = 5;
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(fruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testExecuteTransactions_SupplyOperation() {
        String fruit = "banana";
        int quantity = 30;
        FruitDB.FRUIT_DATA_BASE.put(fruit, quantity);
        List<FruitTransaction> transactions = new ArrayList<>();
        int supplyQuantity = 25;
        transactions.add(new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, fruit, supplyQuantity));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        fruitTransactionStrategy.executeTransactions(transactions);
        int expectedQuantity = 55;
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(fruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testExecuteTransactions_ReturnOperation() {
        String fruit = "mango";
        int quantity = 5;
        FruitDB.FRUIT_DATA_BASE.put(fruit, quantity);
        List<FruitTransaction> transactions = new ArrayList<>();
        int returnQuantity = 3;
        transactions.add(new FruitTransaction(FruitTransaction
                .Operation.RETURN, fruit, returnQuantity));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        fruitTransactionStrategy.executeTransactions(transactions);
        int expectedQuantity = 8;
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(fruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testExecuteTransactions_NotEnoughInventory() {
        String fruit = "peach";
        int quantity = 3;
        FruitDB.FRUIT_DATA_BASE.put(fruit, quantity);
        List<FruitTransaction> transactions = new ArrayList<>();
        int wrongQuantity = 5;
        transactions.add(new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, fruit, wrongQuantity));
        FruitTransactionStrategy fruitTransactionStrategy = new FruitTransactionStrategy();
        assertThrows(RuntimeException.class, () ->
                fruitTransactionStrategy.executeTransactions(transactions));
    }
}

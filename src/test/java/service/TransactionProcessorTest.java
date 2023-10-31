package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitInventory;
import core.basesyntax.FruitTransaction;
import db.DataBase;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionProcessorTest {
    private TransactionProcessor transactionProcessor;
    private StubDataBase stubDataBase;

    @BeforeEach
    public void setUp() {
        stubDataBase = new StubDataBase();
        transactionProcessor = new TransactionProcessor(stubDataBase);
    }

    @Test
    public void testProcessTransactions_Balance() {
        FruitInventory inventory = new FruitInventory();
        stubDataBase.setInventory(inventory);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10));

        transactionProcessor.processTransactions(transactions);

        assertEquals(10, inventory.getFruitQuantity("banana"));
    }

    @Test
    public void testProcessTransactions_Supply() {
        FruitInventory inventory = new FruitInventory();
        inventory.addToInventory("apple", 5);
        stubDataBase.setInventory(inventory);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 15));

        transactionProcessor.processTransactions(transactions);

        assertEquals(20, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void testProcessTransactions_Purchase() {
        FruitInventory inventory = new FruitInventory();
        inventory.addToInventory("banana", 20);
        stubDataBase.setInventory(inventory);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));

        transactionProcessor.processTransactions(transactions);

        assertEquals(15, inventory.getFruitQuantity("banana"));
    }

    @Test
    public void testProcessTransactions_Return() {
        FruitInventory inventory = new FruitInventory();
        inventory.addToInventory("banana", 10);
        stubDataBase.setInventory(inventory);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 3));

        transactionProcessor.processTransactions(transactions);

        assertEquals(13, inventory.getFruitQuantity("banana"));
    }

    @Test
    public void testProcessTransactions_UnknownOperation() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.UNKNOWN, "apple", 5));

        assertThrows(RuntimeException.class, () ->
                transactionProcessor.processTransactions(transactions));
    }

    private static class StubDataBase extends DataBase {
        private FruitInventory inventory;

        public void setInventory(FruitInventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public FruitInventory getOrCreateInventory() {
            if (inventory == null) {
                inventory = new FruitInventory();
            }
            return inventory;
        }
    }
}

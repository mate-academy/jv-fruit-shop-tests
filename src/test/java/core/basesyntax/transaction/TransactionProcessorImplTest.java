package core.basesyntax.transaction;

import static org.junit.Assert.assertTrue;

import core.basesyntax.Operation;
import core.basesyntax.db.Storage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private final TransactionProcessorImpl processor = new TransactionProcessorImpl();
    private final List<FruitTransaction> transactionList = new ArrayList<>();

    @AfterEach
    public void tearDown() throws IOException {
        transactionList.clear();
        Storage.storage.clear();
    }

    @Test
    public void processTransactions_NoTransactions_NoInteractions() {
        List<FruitTransaction> transactions = Arrays.asList();
        processor.processTransactions(transactions);
    }

    @Test
    public void processTransactions_SingleTransaction_CorrectHandling() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 10);
        List<FruitTransaction> transactions = Arrays.asList(transaction);
        processor.processTransactions(transactions);
        int appleQuantity = Storage.storage.get("apple");
        assertTrue(appleQuantity == 10);
    }

    @Test
    public void processTransactions_MultipleTransactions_AllHandled() {
        FruitTransaction transaction1 = new FruitTransaction(Operation.SUPPLY, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(Operation.SUPPLY, "orange", 5);
        FruitTransaction transaction3 = new FruitTransaction(Operation.PURCHASE, "apple", 2);
        FruitTransaction transaction4 = new FruitTransaction(Operation.PURCHASE, "orange", 2);
        List<FruitTransaction> transactions = Arrays.asList(transaction1, transaction2,
                transaction3, transaction4);
        processor.processTransactions(transactions);
        int appleQuantity = Storage.storage.get("apple");
        int orangeQuantity = Storage.storage.get("orange");
        assertTrue(appleQuantity == 8);
        assertTrue(orangeQuantity == 3);
    }
}

package core.basesyntax.services;

import static core.basesyntax.transactions.FruitsTransaction.Operation.BALANCE;
import static core.basesyntax.transactions.FruitsTransaction.Operation.PURCHASE;
import static core.basesyntax.transactions.FruitsTransaction.Operation.RETURN;
import static core.basesyntax.transactions.FruitsTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.transactions.FruitsTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorTest {
    private TransactionProcessor transactionProcessor;
    private Storage storage;
    private FruitsDao fruitsDao;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruitsDao = new FruitsDaoImpl(storage);
        transactionProcessor = new TransactionProcessor(fruitsDao);
    }

    @Test
    void processTransaction_validTransaction_ok() {
        List<FruitsTransaction> transactions = List.of(
                new FruitsTransaction(BALANCE, "apple", 100),
                new FruitsTransaction(SUPPLY, "apple", 20),
                new FruitsTransaction(PURCHASE, "apple", 10),
                new FruitsTransaction(RETURN, "apple", 5)
        );
        transactionProcessor.processTransactions(transactions);
        assertEquals(115, storage.getFruitsInventory().get("apple"));
    }

    @Test
    void processTransactions_unknownOperation_notOk() {
        FruitsTransaction invalidTransaction =
                new FruitsTransaction(null, "banana", 20);
        List<FruitsTransaction> transactions = List.of(invalidTransaction);
        assertThrows(IllegalArgumentException.class,
                () -> transactionProcessor.processTransactions(transactions));
    }

    @Test
    void processTransaction_emptyTransaction_ok() {
        List<FruitsTransaction> transactions = List.of();
        transactionProcessor.processTransactions(transactions);
        assertEquals(0, storage.getFruitsInventory().size());
    }

    @Test
    void processTransaction_multipleFruits_ok() {
        List<FruitsTransaction> transactions = List.of(
                new FruitsTransaction(BALANCE, "apple", 100),
                new FruitsTransaction(BALANCE, "banana", 50),
                new FruitsTransaction(SUPPLY, "apple", 20),
                new FruitsTransaction(PURCHASE, "banana", 30),
                new FruitsTransaction(RETURN, "apple", 10)
        );
        transactionProcessor.processTransactions(transactions);
        assertEquals(130, storage.getFruitsInventory().get("apple"));
        assertEquals(20, storage.getFruitsInventory().get("banana"));
    }
}

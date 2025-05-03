package service.impl;

import db.Storage;
import java.util.List;
import model.FruitTransaction;
import model.Operation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionProcessorImplTest {
    @AfterAll
    static void clearStorage() {
        Storage.STORAGE.clear();
    }

    @Test
    void process_ListFruitTransactionsExpectStorageSize_ok() {
        FruitTransactionProcessorImpl fruitTransactionProcessor
                = new FruitTransactionProcessorImpl();
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 10),
                new FruitTransaction(Operation.PURCHASE, "banana", 7),
                new FruitTransaction(Operation.SUPPLY, "banana", 5),
                new FruitTransaction(Operation.RETURN, "banana", 10)
        );

        fruitTransactionProcessor.process(fruitTransactions);

        Assertions.assertEquals(1, Storage.STORAGE.size());

        List<FruitTransaction> fruitTransactions2 = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 7),
                new FruitTransaction(Operation.SUPPLY, "apple", 5),
                new FruitTransaction(Operation.RETURN, "apple", 10)
        );

        fruitTransactionProcessor.process(fruitTransactions2);

        Assertions.assertEquals(2, Storage.STORAGE.size());
    }
}

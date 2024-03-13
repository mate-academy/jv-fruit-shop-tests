package service.operation;

import dao.StorageDaoImpl;
import db.Storage;
import model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static OperationHandler operation;

    @BeforeAll
    static void setUp() {
        operation = new SupplyOperation(new StorageDaoImpl());
    }

    @Test
    void proceed_existingProduct_ok() {
        Storage.STORAGE.put("apple", 30);
        Transaction transaction = new Transaction(Transaction.Operation.RETURN, "apple", 20);
        operation.proceed(transaction);
        Assertions.assertEquals(Storage.STORAGE.get("apple"), 50);
    }

    @Test
    void proceed_notExistingProduct_ok() {
        Transaction transaction = new Transaction(Transaction.Operation.RETURN, "apple", 20);
        operation.proceed(transaction);
        Assertions.assertEquals(Storage.STORAGE.get("apple"), 20);
    }

    @AfterEach
    void clear() {
        Storage.STORAGE.clear();
    }
}

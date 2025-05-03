package service.operation;

import dao.StorageDaoImpl;
import db.Storage;
import model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler operation;

    @BeforeAll
    static void setUp() {
        operation = new PurchaseOperation(new StorageDaoImpl());
    }

    @Test
    void proceed_validData_ok() {
        Storage.STORAGE.put("apple", 30);
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE, "apple", 20);
        operation.proceed(transaction);
        Assertions.assertEquals(Storage.STORAGE.get("apple"), 10);
    }

    @Test
    void proceed_insufficientAmount_notOk() {
        Storage.STORAGE.put("apple", 10);
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE, "apple", 20);
        Assertions.assertThrows(RuntimeException.class, () -> operation.proceed(transaction),
                "RuntimeException should be thrown when transaction amount is more than storage.");

    }

    @Test
    void proceed_noProductInStorage_notOk() {
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE, "orange", 20);
        Assertions.assertThrows(RuntimeException.class, () -> operation.proceed(transaction),
                "RuntimeException should be thrown when there is no such product in storage.");

    }

    @AfterEach
    void clear() {
        Storage.STORAGE.clear();
    }
}

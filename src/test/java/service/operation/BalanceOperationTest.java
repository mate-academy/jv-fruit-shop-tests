package service.operation;

import dao.StorageDaoImpl;
import db.Storage;
import java.util.Map;
import model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler operation;

    @BeforeAll
    static void setUp() {
        operation = new BalanceOperation(new StorageDaoImpl());
    }

    @Test
    public void proceed_validData_ok() {
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE,
                "apple", 10);
        operation.proceed(transaction);
        Map<String, Integer> expected = Map.of(transaction.getFruit(), transaction.getQuantity());
        Map<String, Integer> actual = Storage.STORAGE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void proceed_zeroAmount_ok() {
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE,
                "apple", 0);
        operation.proceed(transaction);
        Map<String, Integer> expected = Map.of(transaction.getFruit(), transaction.getQuantity());
        Map<String, Integer> actual = Storage.STORAGE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void proceed_negativeAmount_notOk() {
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE,
                "apple", -10);
        Assertions.assertThrows(RuntimeException.class, () -> operation.proceed(transaction),
                "RuntimeException should be thrown when amount is negative.");
    }

    @AfterEach
    void clear() {
        Storage.STORAGE.clear();
    }
}

package core.basesyntax.servise.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.servise.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static TransactionService transactionService;

    @BeforeClass
    public static void beforeClass() {
        transactionService = new TransactionServiceImpl();
    }

    @Test
    public void processData_validInputRecords_Ok() {
        List<String> testRecords = new ArrayList<>();
        testRecords.add("type,fruit,quantity");
        testRecords.add("b,banana,100");
        testRecords.add("s,banana,100");
        testRecords.add("p,banana,150");
        testRecords.add("r,banana,50");
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(Transaction.Operation.BALANCE, "banana", 100));
        expected.add(new Transaction(Transaction.Operation.SUPPLY, "banana", 100));
        expected.add(new Transaction(Transaction.Operation.PURCHASE, "banana", 150));
        expected.add(new Transaction(Transaction.Operation.RETURN, "banana", 50));
        List<Transaction> actual = transactionService.processData(testRecords);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void processData_unknownOperationType_notOk() {
        List<String> testRecords = new ArrayList<>();
        testRecords.add("type,fruit,quantity");
        testRecords.add("a,banana,100");
        transactionService.processData(testRecords);
    }

    @Test (expected = RuntimeException.class)
    public void processData_negativeQuantity_notOk() {
        List<String> testRecords = new ArrayList<>();
        testRecords.add("type,fruit,quantity");
        testRecords.add("b,banana,-10");
        transactionService.processData(testRecords);
    }

    @Test (expected = RuntimeException.class)
    public void processData_invalidQuantityType_notOk() {
        List<String> testRecords = new ArrayList<>();
        testRecords.add("type,fruit,quantity");
        testRecords.add("b,banana,thousand");
        transactionService.processData(testRecords);
    }
}

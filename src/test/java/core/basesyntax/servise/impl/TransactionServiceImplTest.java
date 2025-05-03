package core.basesyntax.servise.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.servise.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static final String TITLE = "type,fruit,quantity";
    private static TransactionService transactionService;
    private List<String> testRecords;

    @BeforeClass
    public static void init() {
        transactionService = new TransactionServiceImpl();
    }

    @Before
    public void initTestRecordsList() {
        testRecords = new ArrayList<>();
        testRecords.add(TITLE);
    }

    @Test
    public void processData_validInputRecords_Ok() {
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
        testRecords.add("a,banana,100");
        transactionService.processData(testRecords);
    }

    @Test (expected = RuntimeException.class)
    public void processData_invalidRecordFourComponents_notOk() {
        testRecords.add("b,banana,100,b");
        transactionService.processData(testRecords);
    }

    @Test (expected = RuntimeException.class)
    public void processData_invalidRecordTwoComponents_notOk() {
        testRecords.add("b, banana");
        transactionService.processData(testRecords);
    }

    @Test (expected = RuntimeException.class)
    public void processData_negativeQuantity_notOk() {
        testRecords.add("b,banana,-10");
        transactionService.processData(testRecords);
    }

    @Test (expected = RuntimeException.class)
    public void processData_invalidQuantityType_notOk() {
        testRecords.add("b,banana,thousand");
        transactionService.processData(testRecords);
    }
}
